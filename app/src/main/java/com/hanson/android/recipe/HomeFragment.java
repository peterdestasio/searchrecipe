package com.hanson.android.recipe;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;
import com.hanson.android.recipe.Helper.ImageHelper;
import com.hanson.android.recipe.Model.CategoryItem;
import com.hanson.android.recipe.Model.RecipeItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.hanson.android.recipe.R.drawable.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ImageHelper imageHelper = new ImageHelper();
    ArrayList<RecipeItem> bestList;
    ArrayList<RecipeItem> newList;

    Date today = new Date();

    public HomeFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check search recipes menu on the slide menu
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_search);
                //connect to search recipes page
                FragmentManager manager = getActivity().getSupportFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack(null).commit();
            }
        });
        GridView newGridView = (GridView)view.findViewById(R.id.GridView_New);

        //Connect DB
        DBHelper dbHelper = new DBHelper(getContext(), "Recipes.db", null, 1);

//        int userCount = dbHelper.user_Allcount();
//        if (userCount == 0)
//        {
//            dbHelper.user_Insert("shyoo","0000");
//        }

        ArrayList<RecipeItem> defaultDataList = dbHelper.recipes_SelectAll();
        if(defaultDataList == null || defaultDataList.size() == 0)
        {
            //Set Bibimap data
            Drawable drawable = getResources().getDrawable(R.drawable.bibimbap, getActivity().getTheme());
            byte[] bibimbap = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Korea", "Bibimbap", "shyjoo", today.toString(),
                    "1. rice \n 2. hubs and egg \n 3. minx", "Korean traditional food",
                    bibimbap, bibimbap, 0);

            int bibmbapId = dbHelper.recipes_GetIdByName("Bibimbap");
            ArrayList<String> bibmbapIngre = new ArrayList<>();
            bibmbapIngre.add("rice");
            bibmbapIngre.add("egg");
            bibmbapIngre.add("sesame oil");
            bibmbapIngre.add("gochujang");
            bibmbapIngre.add("carrot");

            for (int i = 0; i < bibmbapIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(bibmbapId,bibmbapIngre.get(i));
            }

            //Set Bulgogi data
            drawable = getResources().getDrawable(R.drawable.bulgogi, getActivity().getTheme());
            byte[] bulgogi = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Korea", "Bulgogi", "shyjoo", today.toString(),
                    "1. Thinly slice 1 pound of sirloin or tenderloin beef against the grain.\n" +
                            "2. Mix these ingredients to make a marinade:\n" +
                            "\t 2 tbs of soy sauce\n" +
                            "\t 3 tbs of water\n" +
                            "\t 1 tbs of brown sugar\n" +
                            "\t 1 tbs of honey\n" +
                            "\t 1 tbs of sesame oil\n" +
                            "\t 1 tbs of toasted sesame seeds\n" +
                            "\t 2 chopped green onions\n" +
                            "\t 4 cloves of minced garlic\n" +
                            "\t ½ ts of black pepper.\n" +
                            "3. Add the beef to the marinade and keep in the fridge at least 30 minutes. If your cut of beef is tough, you can marinate longer to soften it, or use a Korean pear in the marinade, like I do in this recipe.\n" +
                            "4. Cook it on a pan or a grill, and transfer to a plate or a cast iron plate to serve.\n" +
                            "5. Sprinkle chopped green onion and toasted sesame seeds over top.\n" +
                            "6. Wrap a piece of bulgogi in a lettuce left with a little bit of ssamjang, and put it in your mouth. You can dip carrot or cucumber strips into the ssamjang.", "Korean traditional food",
                    bulgogi, bulgogi, 4);

            int bulgogiId = dbHelper.recipes_GetIdByName("Bulgogi");
            ArrayList<String> bulgogiIngre = new ArrayList<>();
            bulgogiIngre.add("soy sauce");
            bulgogiIngre.add("brown sugar");
            bulgogiIngre.add("sesame oil");
            bulgogiIngre.add("gochujang");
            bulgogiIngre.add("green onion");
            bulgogiIngre.add("onion");

            for (int i = 0; i < bulgogiIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(bulgogiId,bulgogiIngre.get(i));
            }

            //Set Bolognese data
            drawable = getResources().getDrawable(R.drawable.bolognese, getActivity().getTheme());
            byte[] bolognese = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Itay", "Bolognese", "shyjoo", today.toString(),
                    "1. Put the onion and oil in a large pan and fry over a fairly high heat for 3-4 mins. Add the garlic and mince and fry until they both brown. Add the mushrooms and herbs, and cook for another couple of mins.\n" +
                            "2. Stir in the tomatoes, beef stock, tomato ketchup or purée, Worcestershire sauce and seasoning. Bring to the boil, then reduce the heat, cover and simmer, stirring occasionally, for 30 mins.\n" +
                            "3. Meanwhile, cook the spaghetti in a large pan of boiling, salted water, according to packet instructions. Drain well, run hot water through it, put it back in the pan and add a dash of olive oil, if you like, then stir in the meat sauce. Serve in hot bowls and hand round Parmesan cheese, for sprinkling on top.",
                    "Make our traditional spaghetti Bolognese recipe with homemade Bolognese sauce and tender chunks of beef, making this dish a family favourite.",
                    bolognese, bolognese, 6);

            int bologneseId = dbHelper.recipes_GetIdByName("Bolognese");
            ArrayList<String> bologneseIngre = new ArrayList<>();
            bologneseIngre.add("onion");
            bologneseIngre.add("olive oil");
            bologneseIngre.add("spaghetti");
            bologneseIngre.add("Parmesan");
            bologneseIngre.add("mushroom");
            bologneseIngre.add("oregano");
            bologneseIngre.add("tomatoes");
            bologneseIngre.add("beef stock");
            bologneseIngre.add("beef");

            for (int i = 0; i < bologneseIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(bologneseId,bologneseIngre.get(i));
            }

            //Set Chicken Cacciatore data
            drawable = getResources().getDrawable(R.drawable.chickencacciatore, getActivity().getTheme());
            byte[] chickencacciatore = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Itay", "Chicken Cacciatore ", "shyjoo", today.toString(),
                    "1. Combine the flour, salt and pepper in a plastic bag. Shake the chicken pieces in flour until coated. Heat the oil in a large skillet (one that has a cover/lid). Fry the chicken pieces until they are browned on both sides. Remove from skillet.\n" +
                            "2. Add the onion, garlic and bell pepper to the skillet and saute until the onion is slightly browned. Return the chicken to the skillet and add the tomatoes, oregano and wine. Cover and simmer for 30 minutes over medium low heat.\n" +
                            "3. Add the mushrooms and salt and pepper to taste. Simmer for 10 more minutes.",
                    "Many food names reflect various occupations or trades.",
                    chickencacciatore, chickencacciatore, 2);

            int chickencacciatoreId = dbHelper.recipes_GetIdByName("Chicken Cacciatore ");
            ArrayList<String> chickencacciatoreIngre = new ArrayList<>();
            chickencacciatoreIngre.add("flour");
            chickencacciatoreIngre.add("salt");
            chickencacciatoreIngre.add("black pepper");
            chickencacciatoreIngre.add("chicken");
            chickencacciatoreIngre.add("vegetable oil");
            chickencacciatoreIngre.add("onion");
            chickencacciatoreIngre.add("tomatoes");
            chickencacciatoreIngre.add("oregano");
            chickencacciatoreIngre.add("wine");

            for (int i = 0; i < chickencacciatoreIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(chickencacciatoreId,chickencacciatoreIngre.get(i));
            }

            //Set abzhorka data
            drawable = getResources().getDrawable(R.drawable.abzhorka, getActivity().getTheme());
            byte[] abzhorka = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Russia", "Abzhorka", "shyjoo", today.toString(),
                    "1. Put the onion and oil in a large pan and fry over a fairly high heat for 3-4 mins. Add the garlic and mince and fry until they both brown. Add the mushrooms and herbs, and cook for another couple of mins.\n" +
                            "2. Stir in the tomatoes, beef stock, tomato ketchup or purée, Worcestershire sauce and seasoning. Bring to the boil, then reduce the heat, cover and simmer, stirring occasionally, for 30 mins.\n" +
                            "3. Meanwhile, cook the spaghetti in a large pan of boiling, salted water, according to packet instructions. Drain well, run hot water through it, put it back in the pan and add a dash of olive oil, if you like, then stir in the meat sauce. Serve in hot bowls and hand round Parmesan cheese, for sprinkling on top.",
                    "Make our traditional spaghetti Bolognese recipe with homemade Bolognese sauce and tender chunks of beef, making this dish a family favourite.",
                    abzhorka, abzhorka, 3);

            int abzhorkaId = dbHelper.recipes_GetIdByName("Abzhorka");
            ArrayList<String> abzhorkaIngre = new ArrayList<>();
            abzhorkaIngre.add("carrot");
            abzhorkaIngre.add("pickle");
            abzhorkaIngre.add("onion");
            abzhorkaIngre.add("beef");

            for (int i = 0; i < abzhorkaIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(abzhorkaId,abzhorkaIngre.get(i));
            }

            //Set beefstroganoff data
            drawable = getResources().getDrawable(R.drawable.beefstroganoff, getActivity().getTheme());
            byte[] beefstroganoff = imageHelper.getByteArrayFromDrawable(drawable);

            dbHelper.recipes_Insert("Russia", "Beef Stroganoff", "shyjoo", today.toString(),
                    "Chop the meat long wise fibers (fibres) and beat the pieces a little. After that cut the pieces into stripes 2 cm long and 1/2 cm wide. Season and roll them in flour. Fry chopped onion in the pan and when it is gold brown, put the stripes there. Fry on hot heat until the meat is light brown. Make a sauce: fry 1 tb flour pounded with butter for few minutes, add sour cream, ketchup, salt. Pour the sauce over meat and stew on a low heat during 15-20 minutes. Don't let sauce to boil, overwise the meat will be hard. Beef Stroganoff is served with fried potatoes.\n",
                    "Beef stroganoff is a dish consisting of strips of lean beef sauteed and served in a sour-cream sauce with onions and mushrooms. Legend has it that when he was stationed in deepest Siberia, his chef discovered that the beef was frozen so solid that it could only be coped with by cutting it into very thin strips.",
                    beefstroganoff, beefstroganoff, 8);

            int beefstroganoffId = dbHelper.recipes_GetIdByName("Beef Stroganoff");
            ArrayList<String> beefstroganoffIngre = new ArrayList<>();
            beefstroganoffIngre.add("beef");
            beefstroganoffIngre.add("flour");
            beefstroganoffIngre.add("ketchup");
            beefstroganoffIngre.add("sour cream");
            beefstroganoffIngre.add("broth");
            beefstroganoffIngre.add("onion");

            for (int i = 0; i < beefstroganoffIngre.size(); i++)
            {
                dbHelper.ingredients_Insert(beefstroganoffId,beefstroganoffIngre.get(i));
            }
        }

        TextView resultTextView = (TextView) view.findViewById(R.id.txt_DBresult);
        //resultTextView.setText(tempcategory);

        newList = dbHelper.recipes_SelectNew();

        newGridView.setAdapter(new MainRecipeAdapter(this.getContext(), newList, R.layout.fragment_home_recipeitem));

        newGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = newList.get(position);
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe.get_recipeName());
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipeName(),Toast.LENGTH_SHORT).show();
            }
        });


        //connect GrieView code to UI
        GridView bestGridView = (GridView)view.findViewById(R.id.GridView_Best);

        bestList = dbHelper.recipes_SelectBest();

        bestGridView.setAdapter(new MainRecipeAdapter(this.getContext(), bestList, R.layout.fragment_home_recipeitem));

        bestGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                RecipeItem selectRecipe = bestList.get(position);
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("recipe", selectRecipe.get_recipeName());
                startActivity(intent);
                //Toast.makeText(view.getContext(),selectRecipe.get_recipName(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
