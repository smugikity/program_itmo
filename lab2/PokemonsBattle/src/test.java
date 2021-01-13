public class test {
    public static void main(String[] args) {
        B b = new B();
    }
}
class A{
    A() {System.out.println('1');}
    {System.out.println('2');}
    static {System.out.println('3');}
}
class B extends A{
    B() {System.out.println('4');}
    {System.out.println('5');}
    static {System.out.println('6');}
}