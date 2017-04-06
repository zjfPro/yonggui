package com.cg.common.utils;



/**
 * ��ҳ������     ���ܣ���ȡ��ʼ����ͽ�������
 * @author PanHongBin     2011-12-01
 *
 */
public class PageIndex {

	/** ��ʼ���� **/
	private long startIndex;
	/** �������� **/
	private long endIndex;
	
	
	public PageIndex(){}
	
	public PageIndex(long startIndex, long endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public long getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(long endIndex) {
		this.endIndex = endIndex;
	}
	
	/**
	 * ��ȡ��ҳ��ҳ��������
	 * 
	 * @param pageBarSize      �ڷ�ҳ����ÿ����ʾ��ҳ����
	 * @param currentPage      ��ǰҳ
	 * @param totalpage        ��ҳ��
	 */
	public static PageIndex getPageIndex(int pageBarSize,int currentPage,long totalpage){
		int interval=pageBarSize/2;              //������ٸ���
		long startIndex=currentPage;
		long endIndex=0;
		if(startIndex>interval)
		{
			if(totalpage<pageBarSize){
				startIndex=1;
			}else{
				if((totalpage-startIndex)<interval){
					startIndex=startIndex-interval;
					for(int i=1;i<=(currentPage+interval-totalpage);i++){
						startIndex=startIndex-1;
					}
				}else{
					startIndex=startIndex-interval;
				} 
			}
		}
		else
		{
			for(int i=0;i<=interval;i++){
				if(startIndex==(i+1)){
					interval=interval+interval-i;
					break;
				}
			}
			startIndex=1;
		}
		endIndex=(currentPage+interval)>totalpage?totalpage:(currentPage+interval);
		return new PageIndex(startIndex,endIndex);
	}
}
