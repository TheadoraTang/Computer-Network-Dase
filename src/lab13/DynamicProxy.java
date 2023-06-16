package lab13;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * DynamicProxy类持有⼀个被代理类的对象
 * InvocationHandler接⼝⽤于实现动态代理
 * 实现了InvocationHandler接⼝的invoke⽅法，
 * 执⾏所有代理对象的⽅法都会被替换成执⾏接⼝的invoke⽅法
 */
interface IProxy {
    void submit();
}
class PersonA implements IProxy {
    @Override
    public void submit() {
        System.out.println("PersonA提交了⼀份报告");
    }
}
// 编写类PersonB
class PersonB implements IProxy {
    //被代理者的引⽤
    private IProxy m_Person;
    PersonB(IProxy person) {
        m_Person = person;
    }
    @Override
    public void submit() {
        before();
        m_Person.submit();
    }
    public void before() {
        System.out.println("PersonB加上抬头");
    }
}

public class DynamicProxy implements InvocationHandler {
    private Object obj; //被代理类的对象（Object类型），接受任意类型对象
    DynamicProxy(Object _ojb) {
        obj = _ojb;
    }
    @Override
/**
 * 这个⽅法不是我们显式地去调⽤
 * proxy:代表代理对象，
 * method:代表正在执⾏的⽅法，
 * args:代表调⽤⽬标⽅法时传⼊的实参
 */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(obj, args);
        return result;
    }
    private void before() {
        System.out.println("代理对象加上抬头");
    }
}
