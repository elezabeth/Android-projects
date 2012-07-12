package com.qburst.eshop.product;

import com.qburst.eshop.pojo.ClassObject;
import com.qburst.eshop.pojo.Product;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PrintBillActivity extends Activity {

	/** Displays the bill details */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.printlayout);
	    Bundle responses = getIntent().getExtras();
	    String purchaseId = responses.getString("purchaseId");
	    TextView printName = (TextView) findViewById(R.id.printname);
	    TextView printPrice = (TextView) findViewById(R.id.printprice);
	    TextView printComments = (TextView) findViewById(R.id.printcomments);
	    TextView printVendor = (TextView) findViewById(R.id.printvendor);
	    TextView printPurchaseId = (TextView) findViewById(R.id.printpurchaseId);
		Product product = ClassObject.getClassObject().getProduct();
		printName.setText(product.getName());
		printPrice.setText(Integer.toString(product.getPrice()));
		printComments.setText(product.getComments());
		printVendor.setText(product.getVendor());
		printPurchaseId.setText(purchaseId);
	    // TODO Auto-generated method stub
	}

}
