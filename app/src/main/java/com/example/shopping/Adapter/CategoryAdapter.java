package com.example.shopping.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.Fragment.CategoryProductsFragment;
import com.example.shopping.Fragment.DetailFragment;
import com.example.shopping.Model.Category;
import com.example.shopping.Model.Customer;
import com.example.shopping.Model.Product;
import com.example.shopping.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterVH>  {
    private Context context;
    private List<Category> categories;
    private Customer customer;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapterVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_layout, parent, false);

        return new CategoryAdapter.CategoryAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapterVH holder, int position) {
        Category category = categories.get(position);

        holder.categoryTitle.setText(category.getTypeName());
        Glide.with(holder.itemView).load("file:///android_asset/Image/" + category.getImage()).into(holder.categoryImg);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryAdapterVH extends RecyclerView.ViewHolder {
        public TextView categoryTitle;
        public ImageView categoryImg;
        public View itemView;

        public CategoryAdapterVH(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            this.categoryTitle = itemView.findViewById(R.id.category_title);
            this.categoryImg = itemView.findViewById(R.id.category_img);

            Animation rightAnim = AnimationUtils.loadAnimation(getContext(), R.anim.right_animation);
            Animation leftAnim = AnimationUtils.loadAnimation(getContext(), R.anim.left_animation);

            categoryTitle.setAnimation(rightAnim);
            categoryImg.setAnimation(leftAnim);

            this.categoryImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                    FragmentTransaction transaction =
                            fragmentActivity.getSupportFragmentManager().beginTransaction();

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(categoryTitle, "category_title_tran");
                    pairs[1] = new Pair<View, String>(categoryImg, "category_img_tran");

                    Bundle args = new Bundle();
                    Category category = categories.get(getLayoutPosition());

                    args.putString("typeId", category.getTypeId());
                    args.putString("categoryTitle", category.getTypeName());
                    args.putSerializable("info", customer);

                    CategoryProductsFragment categoryProductsFragment = new CategoryProductsFragment();
                    categoryProductsFragment.setArguments(args);

                    transaction.replace(R.id.nav_host_fragment, categoryProductsFragment);
                    transaction.addSharedElement(categoryTitle, "category_title_tran");
                    transaction.addSharedElement(categoryImg, "category_img_tran");

                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }

    }
}
