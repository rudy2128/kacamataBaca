package com.kacamata.kacamatabacauser.ui.cart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kacamata.kacamatabacauser.R;
import com.kacamata.kacamatabacauser.entity.Cart;
import com.kacamata.kacamatabacauser.helper.RupiahHelper;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    final Activity activity;
    List<Cart>cartList = new ArrayList<>();

    public CartAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartViewHolder(view);
    }


    public void setListCart(List<Cart> carts) {
        this.cartList.clear();
        this.cartList.addAll(carts);
        notifyDataSetChanged();
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.tvTitle.setText(cart.getTitle());
        holder.tvPrice.setText(RupiahHelper.formatRupiah(Double.parseDouble(cart.getPrice())));
        holder.tvQty.setText("X" +cart.getQuantity());
        holder.tvSubtotal.setText(RupiahHelper.formatRupiah(Double.parseDouble(cart.getSubtotal())));

    }

  @Override
    public int getItemCount() {
        return cartList.size();
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvPrice,tvQty,tvSubtotal;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQty = itemView.findViewById(R.id.tv_qty);
            tvSubtotal = itemView.findViewById(R.id.tv_subtotal);



        }
    }
}
