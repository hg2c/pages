public class Singleton {
  private static Singleton instance = null;
  private Singleton() { }
  public static Singleton getInstance() {
     if(instance == null) {
        synchronzied(Singleton.class) {
           if(instance == null) {
               instance = new Singleton();  //非原子操作
           }
        }
     }
     return instance;
   }
}


看似简单的一段赋值语句：instance= new Singleton()，但是很不幸它并不是一个原子操作，其实际上可以抽象为下面几条JVM指令：
memory =allocate();    //1：分配对象的内存空间
ctorInstance(memory);  //2：初始化对象
instance =memory;     //3：设置instance指向刚分配的内存地址

上面操作2依赖于操作1，但是操作3并不依赖于操作2，所以JVM是可以针对它们进行指令的优化重排序的，经过重排序后如下：
memory =allocate();    //1：分配对象的内存空间
instance =memory;     //3：instance指向刚分配的内存地址，此时对象还未初始化
ctorInstance(memory);  //2：初始化对象




例子1

有一个全局的状态变量open:
boolean open=true;
这个变量用来描述对一个资源的打开关闭状态，true表示打开，false表示关闭，假设有一个线程A,在执行一些操作后将open修改为false:

//线程A
resource.close();
open = false;

线程B随时关注open的状态，当open为true的时候通过访问资源来进行一些操作:
//线程B
while(open) {
doSomethingWithResource(resource);
}

当A把资源关闭的时候，open变量对线程B是不可见的，如果此时open变量的改动尚未同步到线程B的工作内存中,那么线程B就会用一个已经关闭了的资源去做一些操作，因此产生错误。



public class Test {
    public volatile int inc = 0;
    public void increase() {
        inc++;
    }
    public static void main(String[] args) {
        final Test test = new Test();
        for(int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    for(int j = 0; j< 1000; j++)
                        test.increase();
                };
            }.start();
        }
        while(Thread.activeCount() > 1)
            //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
    }
}

作者：东东东鲁
链接：http://www.jianshu.com/p/b4d4506d3585
來源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
