package com.mylearn.bean;

public class ExamStudent {
    private int flowId;
    private int type;
    private String IDCard;
    private String examCard;
    private String studentName;
    private String location;
    private int grade;

    public ExamStudent() {

    }

    public ExamStudent(int flowId, int type, String IDCard, String examCard, String studentName, String location, int grade) {
        this.flowId = flowId;
        this.type = type;
        this.IDCard = IDCard;
        this.examCard = examCard;
        this.studentName = studentName;
        this.location = location;
        this.grade = grade;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        System.out.println("===============成绩查询=================");
        return info();
    }

    public String info(){
        return "流水号：" + flowId +
                "\n四/六级：" + type +
                "\n身份证号：" + IDCard  +
                "\n准考证：" + examCard +
                "\n学生姓名：" + studentName +
                "\n所在城市：" + location +
                "\n成绩：" + grade ;
    }
}
