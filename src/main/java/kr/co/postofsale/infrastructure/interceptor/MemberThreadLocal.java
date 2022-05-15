package kr.co.postofsale.infrastructure.interceptor;

import java.lang.reflect.Member;

public class MemberThreadLocal {

    private static final ThreadLocal<Member> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
    }

    public static void set(Member member){
        threadLocal.set(member);
    }

    public static Member get(){
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
