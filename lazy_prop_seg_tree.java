/* Online Java Compiler and Editor */
import java.util.*;
public class LazyPropagation{
    
    private int[] segmenttrees,lazy;
    int n;
    public LazyPropagation(int[] arr)
    {
        this.n= arr.length;
        this.segmenttrees= new int[4*n];
        this.lazy= new int[4*n];
        build(0,0,n-1,arr);
    }
    public void build(int node,int s,int e,int[] arr)
    {
        if(s==e)
        {
             segmenttrees[node]=arr[s];
        }
        else{
        int mid=(s+e)/2;
        build(2*node+1,s,mid,arr);
        build(2*node+2,mid+1,e,arr);
        segmenttrees[node]=Math.max(segmenttrees[2*node+1],segmenttrees[2*node+2]);
        }
    }
    
    private void LazyUpdate(int node, int s, int e, int L, int R, int value)
    {
        if(lazy[node]!=0)
        {
            segmenttrees[node]+= lazy[node];
            if(s!=e)
            {
               lazy[2*node+1]+=lazy[node];
               lazy[2*node+2]+=lazy[node];
            }
            lazy[node]=0;
        }
        if(s>R || e<L)    //no match
        {
            return;
        }
        if(s>=L&&e<=R) //complete overlap
        {
            
        
            segmenttrees[node]+= value;
            if(s!=e)
            {
               lazy[2*node+1]+=value;
               lazy[2*node+2]+=value;
            }
        return;
        }
    
    int mid =(s+e)/2;//partial overlap
    LazyUpdate(2*node+1, s,mid,L, R, value);
    LazyUpdate(2*node+2, mid+1,e, L, R, value);
    segmenttrees[node]=Math.max(segmenttrees[2*node+1],segmenttrees[2*node+2]);
    }
    private int LazyQuery(int node, int s, int e, int L, int R)
    {
        if(lazy[node]!=0)
        {
            segmenttrees[node]+= lazy[node];
            if(s!=e)
            {
               lazy[2*node+1]+=lazy[node];
               lazy[2*node+2]+=lazy[node];
            }
            lazy[node]=0;
        }
           if(s>R || e<L) //no match
        {
            return Integer.MIN_VALUE;
        }
        if(s>=L&&e<=R) //complete overlap
        {
        return segmenttrees[node];
        }
        
    
    
    int mid =(s+e)/2;//partial overlap
   int left= LazyQuery(2*node+1, s,mid,L, R);
   int right= LazyQuery(2*node+2, mid+1,e, L, R);
    return Math.max(left,right);
        
    }
    
     public static void main(String []args){
        
         int arr[]={1,2,3,4,5,6};
         LazyPropagation s= new LazyPropagation(arr);
         s.build(0,0,arr.length-1,arr);
         s.LazyUpdate(0,0, arr.length-1,0,3,6);
         int answer= s.LazyQuery(0,0, arr.length-1,2,5);
         System.out.println(answer);
        
         
     }
}