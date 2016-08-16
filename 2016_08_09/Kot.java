public class Kot {
    private int age, height, weight, eyesColor, hairColor, hairLenght;
    private String name = "Violetta";

    public void setAge(int Age){
        age = Age;
    }
    public void setHeight(int Height){
        height = Height;
    }
    public String getName(){
        System.out.println("Kot's name is " + name);
        return name;
    }

    public static void main(String[] args) {
        Kot Vita = new Kot();
        System.out.println("What is the Kot's name?");
        Vita.getName();
    }
}
