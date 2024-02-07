import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

class Vehicle {
    private static final String producedBy="";
    private final int productionYear;
    private final String vin;
    private String plateNumber;
    private double kilometers;
    private int lastSoldOnYear;
    private double positionX;
    private double positionY;

    public Vehicle(int productionYear, String vin) {
        this.productionYear = productionYear;
        this.vin = vin;
    }

    public Vehicle(int productionYear, String vin, String plateNumber, double kilometers, int lastSoldOnYear, double positionX, double positionY) {
        this.productionYear = productionYear;
        this.vin = vin;
        this.plateNumber = plateNumber;
        this.kilometers = kilometers;
        this.lastSoldOnYear = lastSoldOnYear;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Vehicle(){
        this(2000, "undefined","undefined",0,2000,0.0,0.0);
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getKilometers() {
        return kilometers;
    }

    public int getLastSoldOnYear() {
        return lastSoldOnYear;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void sellVehicle(String plateNumber, int lastSoldOnYear) {
        this.plateNumber = plateNumber;
        this.lastSoldOnYear = lastSoldOnYear;
    }

    public void moveVehicle(double newPositionX, double newPositionY) {
        double distance = Math.sqrt(Math.pow((newPositionX - positionX), 2) + Math.pow((newPositionY - positionY), 2));
        this.kilometers += distance;
        this.positionX = newPositionX;
        this.positionY = newPositionY;
    }

    public boolean isVinValid(boolean isDrivingInNorthAmerica) {
        if (isDrivingInNorthAmerica == true)
        {
            int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 1,
                    2, 3, 4, 5, 0, 7, 0, 9, 2, 3,
                    4, 5, 6, 7, 8, 9 };
            int[] weights = { 8, 7, 6, 5, 4, 3, 2, 10, 0, 9,
                    8, 7, 6, 5, 4, 3, 2 };

            String s=this.vin;

            if(s.length() != 17)
                return false;

            s=s.replace("-","");
            s=s.toUpperCase();

            if (s.contains("O") || s.contains("Q") || s.contains("I"))
                return false;

            int sumOfProducts=0;
            for(int i=0;i<17;i++)
            {
                char c=s.charAt(i);
                int value;
                int weight=weights[i];

                if (Character.isLetter(c))
                {
                    int indexInValues=c-'A';
                    value=values[indexInValues];

                    if(value == 0)
                    {
                        return false;
                    }
                }else if (Character.isDigit(c))
                {
                    value = c - '0';
                } else return false;

                sumOfProducts += value * weight;
            }

            sumOfProducts%=11;
            char checkDigit = s.charAt(8);
            if(checkDigit != 'X' && (checkDigit < '0' || checkDigit > '9'))
            {
                return false;
            }
            if(sumOfProducts == 10 && checkDigit == 'X')
            {
                return true;
            }
            else if (sumOfProducts == checkDigit - '0')
            {
                return true;
            }
            return false;
        }

        return true;
    }

    public void printVinDecomposed(){
        System.out.println("Identificatorul producatorului: "+this.vin.substring(0,3)
                +"\nAtributele vehiculului: "+this.vin.substring(3,8)
                + "\nCifra de verificare: "+this.vin.charAt(8)
                + "\nAnul productiei modelului: "+this.vin.charAt(9)
                + "\nSeria fabricii: "+this.vin.charAt(10)
                + "\nIdentificatorul numeric: "+this.vin.substring(11,17));
    }

    public String display(){
        return "Vehicle details - produced by: "+Vehicle.producedBy+", production year: "+this.productionYear+", VIN: "+this.vin+", plate nr: "+this.plateNumber
                +", km: "+this.kilometers+", last sold on year: "+this.lastSoldOnYear+", coordinates: "+this.positionX+", "+this.positionY;
    }
}


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testNumber = scanner.nextInt();
        switch (testNumber) {
            case 1:
                try {
                    int producedByModifs = Vehicle.class.getDeclaredField("producedBy").getModifiers();
                    if (Modifier.isStatic(producedByModifs) && Modifier.isFinal(producedByModifs)) {
                        System.out.println("producedBy field correctly declared");
                    } else {
                        System.out.println("producedBy field incorrectly declared!");
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println("producedBy field not declared!");
                }
                break;
            case 2:
                try {
                    int vinModifs = Vehicle.class.getDeclaredField("vin").getModifiers();
                    if (Modifier.isFinal(vinModifs)) {
                        System.out.println("vin field correctly declared");
                    } else {
                        System.out.println("vin field incorrectly declared!");
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println("vin field not declared!");
                }
                break;
            case 3:
                try {
                    int productionYearModifs =
                            Vehicle.class.getDeclaredField("productionYear").getModifiers();
                    if (Modifier.isFinal(productionYearModifs)) {
                        System.out.println("productionYear field correctly declared");
                    } else {
                        System.out.println("productionYear field incorrectly declared!");
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println("productionYear field not declared!");
                }
                break;
            case 4:
                boolean setPlateNumberExists = false;
                boolean setKilometersExists = false;
                try {
                    Vehicle.class.getDeclaredMethod("setPlateNumber", String.class);
                    setPlateNumberExists = true;
                } catch (NoSuchMethodException e) {
                    System.out.println("plateNumber field should be modifiable!");
                }
                try {
                    Vehicle.class.getDeclaredMethod("setKilometers", double.class);
                    setPlateNumberExists = true;
                    System.out.println("kilometers field should be read only!");
                } catch (NoSuchMethodException e) {
                }
                if (setPlateNumberExists && !setKilometersExists) {
                    System.out.println("plateNumber and kilometers fields correctly declared");
                }
                break;
            case 5:
                int constructorCount = Vehicle.class.getDeclaredConstructors().length;
                if (constructorCount >= 3) {
                    System.out.println("Sufficient number of constructors");
                } else {
                    System.out.println("Insufficient number of constructors!");
                }
                break;
            case 6:
                Vehicle v1 = new Vehicle(1997, "ABCD");
                v1.sellVehicle("1111", 2010);
                System.out.println(v1.getPlateNumber() + " " + v1.getLastSoldOnYear());
                v1.sellVehicle("1234", 2012);
                System.out.println(v1.getPlateNumber() + " " + v1.getLastSoldOnYear());
                break;
            case 7:
                Vehicle v2 = new Vehicle(1997, "ABCD");
                System.out.println(v2.getPositionX() + " " + v2.getPositionY());
                v2.moveVehicle(99, 199);
                System.out.println(v2.getPositionX() + " " + v2.getPositionY());
                break;
            case 8:
                Vehicle v3 = new Vehicle(1997, "ABCD");
                System.out.println(v3.isVinValid(false));
                System.out.println(v3.isVinValid(true));
                Vehicle v4 = new Vehicle(1997, "1M8GDM9AXKP042788");
                System.out.println(v4.isVinValid(false));
                System.out.println(v4.isVinValid(true));
                break;
            case 9:
                Vehicle v5 = new Vehicle(1997, "1M8GDM9AXKP042788");
                v5.printVinDecomposed();
                break;
            case 10:
                try {
                    Method displayMethod = Vehicle.class.getDeclaredMethod("display");
                    if (displayMethod.getReturnType() == String.class) {
                        System.out.println("display method correctly declared");
                    } else {
                        System.out.println("display method incorrectly declared!");
                    }
                } catch (NoSuchMethodException e) {
                    System.out.println("display method not declared!");
                }
                break;
        }
    }
}
