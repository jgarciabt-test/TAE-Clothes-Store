package com.tae.store.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tae.store.R;
import com.tae.store.adapters.BagListAdapter;
import com.tae.store.helpers.DatabaseHandler;
import com.tae.store.model.Product;

public class BagFragment extends SherlockListFragment {

	private TextView qty;
	private TextView total;
	private TextView total_order;
	private AlertDialog aDialog;

	private DatabaseHandler BAG;
	private ArrayList<Product> myBag;
	private BagListAdapter adapter;

	private int position = -1;

	public BagFragment() {

		myBag = new ArrayList<Product>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootGroup = (ViewGroup) inflater.inflate(
				R.layout.fragment_bag, null, false);

		qty = (TextView) rootGroup.findViewById(R.id.txt_bag_qty);
		total_order = (TextView) rootGroup.findViewById(R.id.txt_bag_order);
		total = (TextView) rootGroup.findViewById(R.id.txt_bag_main_total);

		BAG = new DatabaseHandler(getActivity().getBaseContext());

		if (savedInstanceState == null) {
			if (myBag.isEmpty()) {
				myBag = BAG.getBag();
			}
		} else {
			myBag = savedInstanceState.getParcelableArrayList("myBag");
		}
		adapter = new BagListAdapter(getActivity(), myBag);
		setListAdapter(adapter);

		updateSreen();

		return rootGroup;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
				vibrator.vibrate(200);
				BagFragment.this.position = position;
				new AlertDialog.Builder(getActivity())
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage(R.string.delete_dialog)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										deleteProduct();
									}

								}).setNegativeButton(R.string.no, null).show();
				return false;
			}
		});

	}

	private void updateSreen() {
		qty.setText(String.valueOf(myBag.size()));
		float totalToPay = 0;
		for(int i=0;i<myBag.size();i++){
			totalToPay+=myBag.get(i).getPrice();
		}
		total_order.setText(String.valueOf(totalToPay));
		total.setText(String.valueOf(totalToPay));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelableArrayList("myBag", myBag);
	}

	public void deleteProduct() {
		BAG.deleteProduct(myBag.get(position).getId());
		myBag.remove(position);
		adapter.notifyDataSetChanged();
		updateSreen();
	}

}
