package TasteProfiling;

public class Recipe {
    public int id;
    public String name;
    public String category;
    public String ingredients;
    public String instructions;
    public String servingSize;
    public String cookingTime;
    public String notes;
    public String rating;

    public Recipe(int id, String name, String category, String ingredients, String instructions,
                  String servingSize, String cookingTime, String notes, String rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.servingSize = servingSize;
        this.cookingTime = cookingTime;
        this.notes = notes;
        this.rating = rating;
    }
}