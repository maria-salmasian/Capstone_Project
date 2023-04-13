package com.example.capstone.utils.enumeration;

public enum RoleName {
    ADMIN(1){
        public String toString(){
            return "admin";
        }
    },
    PROFESSOR(2){
        public String toString(){
            return "professor";
        }
    },
    STUDENT(3){
        public String toString(){
            return "student";
        }
    };
    private int id;
    RoleName(int id) {
        this.id =id;
    }

    public int getId() {
        return id;
    }

    public static RoleName  getById(int id)  {
        for(RoleName type : values()) {
            if(type.id == (id))
                return type;
        }

        return null;
    }


}
