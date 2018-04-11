package com.example.demo.sort;

public class InsertSort {
	
	
	public void insertSort(int[] num) {
		int tem;
		if(null == num || num.length == 1) {
			return;
		}
		for(int i = 1; i < num.length; i++ ) {//从第二个元素开始遍历
			tem = num[i];
			int j = i - 1;
			while(j > 0 && num[j] > tem ) {//如果当前已经排好序的 值大于当前位置的值 排好序的元素向后移动
				num[j+1] = num[j];
				j--;
			}
			num[j] = tem;//插入元素
		}
	}

}
