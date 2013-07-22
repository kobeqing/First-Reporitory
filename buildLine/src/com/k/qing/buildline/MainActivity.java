package com.k.qing.buildline;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity {

	private List<String> groupData;
	private List<List<String>> childrenData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TabHost tabHost = (TabHost) findViewById(R.id.myTabHost);

		this.initExpandableData();

		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.mainExpandableListView);
		expandableListView.setAdapter(new ExpandableAdapter());

		// needed
		tabHost.setup();

		tabHost.addTab(tabHost.newTabSpec("tab1")
				.setIndicator("tab1 indicator").setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab2")
				.setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(R.id.view3));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initExpandableData() {
		groupData = new ArrayList<String>();
		groupData.add("����");
		groupData.add("����");
		groupData.add("����");

		childrenData = new ArrayList<List<String>>();
		List<String> Child1 = new ArrayList<String>();
		Child1.add("���");
		Child1.add("κ��");
		Child1.add("���");
		childrenData.add(Child1);
		List<String> Child2 = new ArrayList<String>();
		Child2.add("����");
		Child2.add("�ŷ�");
		Child2.add("��Τ");
		Child2.add("����");
		Child2.add("�ܲ�");
		Child2.add("����");
		Child2.add("����");
		Child2.add("���");
		childrenData.add(Child2);
		List<String> Child3 = new ArrayList<String>();
		Child3.add("�������µ�");
		Child3.add("�ɰ���ìǹ");
		Child3.add("��ֽ�");
		Child3.add("���빭");
		Child3.add("����ǹ");
		childrenData.add(Child3);
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int arg0, int arg1) {
			return childrenData.get(arg0).get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			TextView myText = null;
			if (arg3 != null) {
				myText = (TextView) arg3;
				myText.setText(childrenData.get(arg0).get(arg1));
			} else {
				myText = createView(childrenData.get(arg0).get(arg1));
			}
			return myText;
		}

		@Override
		public int getChildrenCount(int arg0) {
			return childrenData.get(arg0).size();
		}

		@Override
		public Object getGroup(int arg0) {
			return groupData.get(arg0);
		}

		@Override
		public int getGroupCount() {
			return groupData.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2,
				ViewGroup arg3) {
			TextView myText = null;
			if (arg2 != null) {
				myText = (TextView) arg2;
				myText.setText(groupData.get(arg0));
			} else {
				myText = createView(groupData.get(arg0));
			}
			return myText;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		private TextView createView(String content) {
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 80);
			TextView myText = new TextView(MainActivity.this);
			myText.setLayoutParams(layoutParams);
			myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			myText.setPadding(80, 0, 0, 0);
			myText.setText(content);
			return myText;
		}

	}

}
