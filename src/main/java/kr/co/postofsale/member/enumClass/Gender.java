package kr.co.postofsale.member.enumClass;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum Gender {


    MALE("남성"),
    FEMALE("여성");

    private String gender;

    public static Gender of(String gender){
        return Arrays.stream(Gender.values())
                .filter(r->r.toString().equalsIgnoreCase(gender))
                .findAny().orElseThrow(()-> new RuntimeException("남성, 여성 중 골라주세요"));
    }
}
