package org.example.ProcessCount;

public class StudentCount {
    String department;
    int count;

    public void setCount(int count)
    {
        this.count=count;
    }

    public void setDepartment(String department)
    {
        this.department=department;
    }

    public int getCount(){
        return count;
    }
    public String getDept(){
        return department;
    }
}
