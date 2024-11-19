package refrigerator;

import java.io.*;
import java.util.*;

public class FileManage {
    private static final String FOOD_PATH = "data/food/";
    private static final String FOOD_EXTENSION = ".bin";
    private static final String RECIPE_PATH = "data/recipe/";
    private static final String RECIPE_EXTENSION = ".txt";

    public static void inputToVector(Scanner in, List<String> v) {
        String temp = in.nextLine();
        String[] words = temp.split(" ");
        Collections.addAll(v, words);
    }

    public static String getRecipe(String key) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(RECIPE_PATH +
                key + RECIPE_EXTENSION));
        StringBuilder result = new StringBuilder(key + " recipe\n");
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        reader.close();
        return result.toString();
    }

    public static void setRecipeUseCin(String key) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(RECIPE_PATH +
                key + RECIPE_EXTENSION));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter recipe (type 'end' to finish):");
        while (true) {
            String temp = scanner.nextLine();
            if (temp.equals("end")) {
                break;
            }
            writer.write(temp);
            writer.newLine();
        }
        writer.close();
    }

    public static Food getFood(String key) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(FOOD_PATH +
                key + FOOD_EXTENSION));

        int expiry = dis.readInt();
        int nameLength = dis.readInt();
        byte[] nameBytes = new byte[nameLength];
        dis.readFully(nameBytes);
        String name = new String(nameBytes);
        dis.close();
        System.out.println("\n" + name + " loaded\n");
        return new Food(name, expiry);
    }

    public static void setFoodUseCin() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter food (name expiry):");
        List<String> inputTemp = new ArrayList<>();
        inputToVector(scanner, inputTemp);
        Food f = new Food(inputTemp.get(0), Integer.parseInt(inputTemp.get(1)));
        setFood(f, f.getName());
    }

    public static void setFood(Food f, String key) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(FOOD_PATH
                + key + FOOD_EXTENSION));
        dos.writeInt(f.getExpiry());
        dos.writeInt(f.getName().length());
        dos.writeBytes(f.getName());
        dos.close();
        System.out.println("\n" + f.getName() + FOOD_EXTENSION + " saved\n");
    }
}