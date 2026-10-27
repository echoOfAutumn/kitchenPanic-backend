import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static Map<String, Integer> productStorage = new HashMap<>();

    public static void main(String[] args) {

        productStorage.put("Томат", 5);
        productStorage.put("Сыр", 3);
        productStorage.put("Тесто", 4);

        cookingMeal("Стейк");

        System.out.println("Повар пошел мыть посуду, пока стейк жарится...");

        try {
            getProduct("Томат", 15);
        } catch (IllegalArgumentException e){
            System.out.println("ОШИБКА: " + e.getMessage());
        }

        try {
            addProduct("Тесто", 2);
        } catch (IllegalArgumentException e){
            System.out.println("ОШИБКА: " + e.getMessage());
        }

        System.out.println(scarceProducts());

        CourierBox<String> box = new CourierBox<>();
        box.setData("Заказ #1 готов");
        System.out.println(box.getData());
    }

    public static void addProduct(String productName, int amount){

        if (!productStorage.containsKey(productName)){
            throw new IllegalArgumentException("Такого продукта нет!");
        }

        if (amount < 0){
            throw new IllegalArgumentException("Количество продукта не может быть меньше нуля!");
        }

        int currentAmount = productStorage.get(productName);

        productStorage.put(productName, currentAmount + amount);
        System.out.println(productName + " добавлен на склад. Общее количество продукта: " + (currentAmount+amount));
    }

    public static void getProduct(String productName, int amount){

        if (!productStorage.containsKey(productName)){
            throw new IllegalArgumentException("Такого продукта нет!");
        }

        if (amount < 0){
            throw new IllegalArgumentException("Количество продукта не может быть меньше нуля!");
        }

        if (amount > productStorage.get(productName)){
            throw new IllegalArgumentException("Количество продукта не может превышать количества на складе!");
        }

        int currentAmount = productStorage.get(productName);

        productStorage.put(productName, currentAmount - amount);
        System.out.println(productName + " взят со склада в количестве: " + amount + ". Осталось: " + (currentAmount - amount));
    }

    public static void cookingMeal(String meal){

        Thread cookingThread = new Thread(() -> {
            System.out.println("Началась готовка блюда: " + meal);
            try{
                Thread.sleep(4000);
            } catch(InterruptedException e){
                System.out.println("Ошибка готовки!");
            }
            System.out.println(meal + " приготовлен");
        });

        cookingThread.start();
    }

    public static List<String> scarceProducts() {

        List<String> list = productStorage.keySet().stream()
                .filter(product -> productStorage.get(product) < 5)
                .map(product -> product.toUpperCase())
                .toList();

        return list;
    }
}