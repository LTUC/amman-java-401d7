package com.LTUC.springSecurityIndustryWorld.Enums;

public enum CODE {
    OK(200),
    DECLINED(450),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    UNPROCESSABLE_ENTITY(422),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403);
    final int id;

    CODE(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
