package kr.co.postofsale.infrastructure.interceptor;

import kr.co.postofsale.member.MemberEntity;

public class MemberThreadLocal {

    private static final ThreadLocal<MemberEntity> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
    }

    public static void set(MemberEntity memberEntity){
        threadLocal.set(memberEntity);
    }

    public static MemberEntity get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

    /*
    To do
    -> theadLocal 로그아웃 시 remove() 해주는 메소드
     */
}
