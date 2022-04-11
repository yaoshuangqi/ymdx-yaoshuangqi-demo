package com.ysq.data;

import java.util.Scanner;

/**
 * @Desc 数组模拟队列
 * @Author Mr.Yao
 * @Date 2022/3/29 14:36
 * @Version 1.0
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);

        char key = ' ';
        final Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("a(add)添加数据");
            System.out.println("g(get)取出数据");
            System.out.println("s(show)显示数据");
            System.out.println("e(exit)退出程序");
            //接收一个字符串
            key = scanner.next().charAt(0);
            switch (key){
                case 'a':
                    int i = scanner.nextInt();
                    arrayQueue.add(i);
                    break;
                case 'g':
                    try {
                        int i1 = arrayQueue.get();
                        System.out.println("取出数据为："+i1);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    try {
                        arrayQueue.show();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    System.out.println("e(exit):退出程序");
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("退出成功");


    }
}

/**
 * 定义一个队列
 */
class ArrayQueue{

    int maxSize;
    int front, rear;
    int[] arr;

    public ArrayQueue(int maxSize){
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1;
        rear = -1;
    }

    /**
     * 队列是否已满
     * @return
     */
    public boolean isFull(){
        return rear == maxSize - 1;
    }

    /**
     * 队列是否为空
     * @return
     */
    public boolean isEmpty(){
        return front == rear;
    }

    /**
     * 增加数据
     * @param n
     */
    public void add(int n){
        if(isFull()){
            System.out.println("队列已满，不能加入数据");
            return;
        }
        rear++;
        arr[rear] = n;
    }

    /**
     * 取出数据
     * @return
     */
    public int get(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法获取数据");
        }
        front++;
        return arr[front];
    }

    /**
     * 显示当前队列数据
     */
    public void show(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，无法获取数据");
        }
        for (int i : arr) {
            System.out.print(i + "\n");
        }
    }

}
