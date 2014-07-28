package air.balloon.keyboard;

import java.util.Random;



import android.util.Log;

public class OrderEncoder {
	
	private int times = 100;
	private int[] Pai=new int[10];
	
	public int[] reOrder()
	{
		//初始化 生成牌
        for (int i = 0; i <10; i++)
        {
        	Pai[i] = i;
        }
        
        int k,temp;
		Random rnd = new Random();
		///洗牌
        for (int num = 0; num < times; num++)
        {
            for (int i = 0; i < 10; i++)
            {
                k = rnd.nextInt() % 10;
               // Log.i("k",Integer.toString(k));
                if(k>=0 && k<=9)
                {	                
	                temp = Pai[k];
	                Pai[k] = Pai[i];
	                Pai[i] = temp;  
                }
                else{
                	i++;
                }
            }
        }	
        
       // for(int i=0;i<10;i++)
       // 	Log.i("order",Integer.toString(Pai[i]));
        
        return Pai;
	}
}
