import java.util.*; 
class Main extends Thread { 
    static ArrayList l = new ArrayList(); 
    public void run() 
    { 
        try { 
            Thread.sleep(2000); 
        } 
        catch (InterruptedException e) {
        } 
        l.add("E4"); 
    } 
  
    public static void main(String[] args) throws InterruptedException 
    { 
        l.add("E1"); 
        l.add("E2"); 
        l.add("E3"); 
        new Main().start(); 
  
        Iterator i = l.iterator(); 
        while (i.hasNext()) { 
            String s = (String)i.next(); 
            System.out.println(s); 
            Thread.sleep(6000); 
        } 
        System.out.println(l); 
    } 
} 
