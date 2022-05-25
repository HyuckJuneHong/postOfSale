package kr.co.postofsale.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import kr.co.postofsale.infrastructure.exception.*;
import kr.co.postofsale.member.MemberEntity;
import kr.co.postofsale.member.MemberRepository;
import kr.co.postofsale.member.MemberRepositoryJDBC;
import kr.co.postofsale.member.enumClass.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    final String User = "identity";

    //토큰 유효시간 Ex) 60분 = 1000L * 60 * 60
    private final long ACCESS_EXPIRE = 1000 * 60 * 30; //엑세스 만료 (30분)
    private final long REFRESH_EXPIRE = 1000 * 60 * 60 * 24 * 14; //새로고침 만료 (2주)

    private final MemberRepositoryJDBC memberRepository;

    @Autowired
    public JwtTokenProvider(MemberRepositoryJDBC memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** init(), @PostConstruct
     * init() : 객체 초기화, secretKey를 Base64로 인코딩.
     * @PostConstruct : 의존성 주입이 이루어진 후 초기화를 수행하는 메서드.
     *      -> 즉, 클래스가 service(로직을 탈 때? 로 생각 됨)를 수행하기 전에 발생.
     *      -> 때문에, 이 메서드는 다른 리소스에서 호출되지 않는다해도 수행
     */
    @PostConstruct
    protected void init() {
        /**
         * 인코딩 or 암호화 Ex : "Hello" -> Base64 형태
         * 디코딩 or 복호화 Ex : Base64 형태 -> "Hello"
         */
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()); //시크릿 키 인코딩
    }

    /**
     * 사용자 정보를 통해 Claims 객체를 만들어서 반환하는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자 정보를 포함한 Claims 객체
     */
    private Claims generateClaims(String identity, MemberRole role, String name){
        Claims claims = Jwts.claims();  //클레임은 name / value 의 한 쌍으로 이루어져 있다.
        claims.put(User, identity);
        claims.put("role", role.toString());
        claims.put("name",name);

        return claims;
    }

    /**
     * 사용자 정보를 통해 AccessToken 을 만드는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자의 AccessToken
     */
    public String createAccessToken(String identity, MemberRole role, String name){

        Date issueDate = new Date();    //토큰 발행 시각

        Date expireDate = new Date();   //토큰 유효 시간
        expireDate.setTime(issueDate.getTime() + ACCESS_EXPIRE); //현재 시각 + 30분

        //HS256 = sha256 + 대칭키를 사용하는 암호화 방식
        return Jwts.builder()                                       //jjwt library의 Jwts로부터 jwt을 생성
                .setHeaderParam("typ", "JWT")           //JWT Header가 지닐 정보들을 담음. / 타입(typ)이 JWT임을 명시
                .setClaims(generateClaims(identity, role, name))    //토큰에서 사용할 정보의 조각들인 클레임 설정
                .setIssuedAt(issueDate)                             //발급 시각 Payload에 담기 위해
                .setSubject("AccessToken")                          //토큰 제목 즉, 토큰 용도
                .setExpiration(expireDate)                          //만료 시각 Payload에 담기 위해
                .signWith(SignatureAlgorithm.HS256, generateKey())  //복호화 시 사용할 Signature 설정. 해싱할 알고리즘과 비밀키를 필요로 함.
                .compact();                                         //토큰 생성
    }

    /**
     * RefreshToken 을 이용하여 AccessToken 을 만들어내는 메서드
     * @param refreshToken 사용자의 RefreshToken
     * @return 사용자의 새로운 AccessToken
     */
    public String createAccessToken(String refreshToken){
        MemberEntity memberEntity = findMemberByToken(refreshToken);  //토큰 발급

        if(!memberEntity.getRefreshToken().equals(refreshToken)) //토큰이 다르다면, 예외 처리
            throw new UnauthorizedException("해당 기능을 사용할 수 없습니다.", 403);

        return createAccessToken(memberEntity.getIdentity()
                , memberEntity.getMemberRole(), memberEntity.getName());
    }

    /**
     * 사용자 정보를 통해 RefreshToken 을 만드는 메서드
     * @param identity 사용자 아이디
     * @param role 사용자 권한
     * @param name 사용자 이름
     * @return 사용자의 RefreshToken
     */
    public String createRefreshToken(String identity, MemberRole role, String name){
        Date issueDate = new Date();
        Date expireDate = new Date();
        expireDate.setTime(issueDate.getTime() + REFRESH_EXPIRE);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(generateClaims(identity, role,name))
                .setIssuedAt(issueDate)
                .setSubject("RefreshToken")
                .setExpiration(expireDate) //유효시간 이주일
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    /**
     * 키 변환을 위한 key를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey() {
        try{
            return SECRET_KEY.getBytes("UTF-8");    //시크릿 키 반환 (즉, 시크릿 키 디코딩)
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage()); //실패 시 자신의 키가 아님.
        }
    }

    /**
     * 토큰의 유효성을 판단하는 메서드
     * @param token 토큰
     * @return 토큰이 만료되었는지에 대한 불리언 값
     * @exception ExpiredJwtException 토큰이 만료되었을 경우에 발생하는 예외
     */
    public boolean isUsable(String token) {

        try{
            Jwts.parser()                           //구성한 다음 JWT 문자열을 구문 분석하는 데 사용할 수 있는 JwtParser 인스턴스
                    .setSigningKey(generateKey())   //이전에 설정된 키를 덮어씁니다. (반환: 메서드 연결을 위한 파싱)
                    .parseClaimsJws(token);         //해당 토큰 문자열이 클레임 Jws로 파싱 (파싱 및 검증, 실패 시 Exception
            //parseClaimsJws 반환: 지정된 컴팩트 클레임 JWS 문자열을 반영하는 Jws 인스턴스
            return true;
        }catch (SignatureException e) {
            log.error("Invalid JWT signature");
            throw new JwtTokenInvalidException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException();
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT claims");
            throw new JwtTokenInvalidException("Empty JWT claims");
        }
    }

    /**
     * 헤더에 있는 토큰을 추출하는 메서드
     * 평소에는 AccessToken을 담아서 주고 받다가 만료가 되었다는 예외가 발생하면 그때 Refresh만
     * @param request 사용자의 요청
     * @return AccessToken 과 RefreshToken 을 담은 객체를 Optional로 감싼 데이터
     */
    public Optional<String> resolveToken(HttpServletRequest request) {

        //ofNullable(): null 이어도 예외 던지지 않고 비어있는 Optional 객체 생성
        return Optional.ofNullable(request.getHeader("Authorization"));
    }

    /** 1. JwtParser 가져오고 -> 키를 덮어쓴 후 -> 해당 token을 클레임 Jws로 파싱 -> JWT BODY의 String 또는 Claims 인스턴스
     * 토큰을 이용하여 사용자 아이디를 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findIdentityByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get(User); //파라미터(key) – 관련 값이 반환될 키 / return : 지정된 키가 매핑되는 값, 키에 대한 매핑이 없으면  null
    }

    /**
     * 토큰을 이용하여 사용자 권한을 찾는 메서드
     * @param token 토큰
     * @return 사용자의 아이디
     */
    public String findRoleByToken(String token){
        return (String) Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

    /**
     * 토큰을 통해 Member 객체를 가져오는 메서드
     * @param token : 토큰
     * @return : jwt 토큰을 통해 찾은 Member 객체
     * @Exception UserNotFoundException : 해당 회원을 찾을 수 없는 경우 발생하는 예외
     */
    public MemberEntity findMemberByToken(String token){
        return memberRepository.findByIdentity(findIdentityByToken(token)) //해당 아이디를 가지는 member를 찾아 토큰을 반환
                .orElseThrow(() -> new NotFoundException("MemberEntity")); //없으면 Exception
    }

    /**
     * 토큰을 통해서 Authentication 객체를 만들어내는 메서드
     * @param token 토큰
     * @return 사용자 정보를 담은 UsernamePasswordAuthenticationToken 객체
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails =
                //토큰을 통해 사용자 아이디를 찾고 해당 권한을 찾아 해당 권한 기능을 가진 User를 생성
                new User(findIdentityByToken(token),
                        "",
                        getAuthorities(MemberRole.of(findRoleByToken(token)))); //사용자에게 부여된 권한을 반환합니다.

        /**
         * 이 생성자는 신뢰할 수 있는(즉, isAuthenticated() = true) 인증 토큰 생성에 만족하는 AuthenticationManager
         * 또는 AuthenticationProvider 구현에서만 사용해야 합니다.
         */
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 권한을 읽어서 해당 유저의 권한이 무엇이 인지 Set에 저장하는 메서드
     * @param role 권한
     * @return 권한 정보를 담은 Set 객체
     */
    private Set<? extends GrantedAuthority> getAuthorities(MemberRole role) {
        Set<GrantedAuthority> set = new HashSet<>();

        if(role.equals(MemberRole.ROLE_ADMIN)){
            set.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(role.equals(MemberRole.ROLE_MANAGER)) {
            set.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }else{
            set.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }

        return set;
    }
}
