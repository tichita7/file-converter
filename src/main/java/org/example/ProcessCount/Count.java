package org.example.ProcessCount;
import org.example.Reader.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Count {

    public static List<StudentCount> countfunc(List<Student> stu)
    {
        HashMap<String, Integer> map = new HashMap<>();

        for(Student s:stu)
        {
            if(map.containsKey(s.getDept()))
            {
                Integer count=map.get(s.getDept());
                map.put(s.getDept(),count+1);
            }
            else
            {
                map.put(s.getDept(),1);
            }
        }
        System.out.println("\n");
        ArrayList<StudentCount> countarray=new ArrayList<>();
        for(HashMap.Entry<String,Integer> m:map.entrySet())
        {
            StudentCount sc= new StudentCount();
            sc.setDepartment(m.getKey());
            sc.setCount(m.getValue());
            countarray.add(sc);
        }


       return countarray;



    }


}
