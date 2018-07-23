package id.codecast.roomexample;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import id.codecast.roomexample.entity.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProductList();
    }

    private void displayProductList(final List<Product> products) {
        RecyclerView listProduct = findViewById(R.id.listProduct);
        listProduct.setLayoutManager(new LinearLayoutManager(this));
        listProduct.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new RecyclerView.ViewHolder(view){ };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                View view = holder.itemView;
                final Product product = products.get(holder.getAdapterPosition());

                TextView name = view.findViewById(R.id.name);
                TextView picture = view.findViewById(R.id.picture);
                TextView price = view.findViewById(R.id.price);

                name.setText(product.name);
                picture.setText(product.image);
                price.setText("$" + product.price);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        displayItemProduct(product);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return products.size();
            }
        });
    }

    private void displayItemProduct(final Product product) {
        View view = LayoutInflater.from(this).inflate(R.layout.create_product, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detail product");
        builder.setView(view);

        final EditText inputName = view.findViewById(R.id.name);
        final EditText inputPicture = view.findViewById(R.id.picture);
        final EditText inputPrice = view.findViewById(R.id.price);

        inputName.setText("T'shirt One");
        inputPicture.setText("tshirt.jpg");
        inputPrice.setText("150");

        inputName.setEnabled(false);
        inputPicture.setEnabled(false);
        inputPrice.setEnabled(false);

        builder.setCancelable(true);

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displayEditProduct(product);
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        App.database.getProductDao().delete(product);
                        return null;
                    }
                }.execute();

                dialogInterface.dismiss();
                getProductList();
                Toast.makeText(MainActivity.this, "Product " + product.name + " deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void displayEditProduct(final Product product) {
        View view = LayoutInflater.from(this).inflate(R.layout.create_product, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit product");
        builder.setView(view);

        final EditText inputName = view.findViewById(R.id.name);
        final EditText inputPicture = view.findViewById(R.id.picture);
        final EditText inputPrice = view.findViewById(R.id.price);

        inputName.setText("T'shirt One");
        inputPicture.setText("tshirt.jpg");
        inputPrice.setText("150");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                product.name = inputName.getText().toString();
                product.image = inputPicture.getText().toString();
                product.price = Integer.parseInt(inputPrice.getText().toString());
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        App.database.getProductDao().update(product);

                        return null;
                    }
                }.execute();

                dialogInterface.dismiss();
                getProductList();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_product){
            createNewProduct();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewProduct() {
        View view = LayoutInflater.from(this).inflate(R.layout.create_product, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new product");
        builder.setView(view);

        final EditText inputName = view.findViewById(R.id.name);
        final EditText inputPicture = view.findViewById(R.id.picture);
        final EditText inputPrice = view.findViewById(R.id.price);

        inputName.setText("T'shirt One");
        inputPicture.setText("tshirt.jpg");
        inputPrice.setText("150");

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Product product = new Product();
                product.id = System.currentTimeMillis();
                product.name = inputName.getText().toString();
                product.image = inputPicture.getText().toString();
                product.price = Integer.parseInt(inputPrice.getText().toString());
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        App.database.getProductDao().create(product);

                        return null;
                    }
                }.execute();

                dialogInterface.dismiss();
                getProductList();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    @SuppressLint("StaticFieldLeak")
    public void getProductList() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final List<Product> products = App.database.getProductDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayProductList(products);
                    }
                });
                return null;
            }
        }.execute();
    }
}
