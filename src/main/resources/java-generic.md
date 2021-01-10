# 제네릭

처음 봤을 때부터 이끌렸었는데... 긴 시간을 같이 보내고서야 이제야 좀 익숙해진 것 같습니다.
좀처럼 감이 안잡히기도 했고, 뭔가 자주 쓰지 않는 영역이다보니 공부해도 나중에 다시 기억이 안나는 그런 분야여서... 
또, 요즘은 개발툴이 너무 좋다보니, 제네릭 관련 뭐가 안되면 그냥 인텔리제이 같은 툴이 알려주는 대로 따라하면 해결되는 등... 
이런 저런 이유로 인해 제네릭에 대한 공부를 소홀히 했습니다. 그래서 스스로 정리할 겸, 블로그 글로 올려볼까 합니다.
기능에 대한 정리보다는 왜 그런지에 대해서 조금 따져보고 싶어서 내용을 풀어쓴 거라 이미 관련 내용을 아시는 분드링라면 스킵하시는 게 시간 절약일 수 있을 것 같습니다. 
혹여라도 읽고 잘못된 점 있으면 언제든지 말씀주시면 감사하겠습니다.

## tl;dr;

그래도 요약본은 있어야 겠죠. ^^;;

- 제네릭은 런타임과 관련이 없는 컴파일 타임에 타입 체크를 더 엄밀하게 해주는 기능이다.
- 매개변수 타입을 완화하고 싶으면 매개변수 타입 반환 함수를 포기하거나 매개변수 타입을 인자로 받는 함수 중 하나는 포기해야 한다.

## 제네릭이 없을 때

제네릭이 자바 5에서 생겼는데, 제네릭이 생기기 전에는 다음과 같은 코드가 발생했습니다. 

요구사항이 아무 객체나 담는 클래스가 필요하다고 했을 때..
```java
public class RawJBox {

    private final Object element;
    public RawJBox(Object element) {
        this.element = element;
    }

    public Object get() {
        return this.element;
    }

}
```
위와 같이 선언하고 아래와 같이 쓰는데... 

주석에 달았다시피 컴파일 타임과 런타임의 불일치가 너무 쉽게 발생합니다.
```java
    // RawJBox는 Object를 생성자 함수 인자로 받는다.
    // 1. generic이 없을 때 문제 
    RawJBox rawBox = new RawJBox(new JCat());
    JCat amucat = rawBox.get(); // compile error!! 실제 인스턴스의 클래스와는 상관없이 에러가 난다.
    // !!!!!!!!!!!!!!!!!!!!!!!!!!
    JLion possibleLion = (JLion) rawBox.get(); // no compile error!! 실제 인스턴스의 클래스가 아닌데도...

```

제네릭 타입은 이런 문제를 해결해줍니다.


## 제네릭이란? 

그래서 제네릭이 뭘까요? 

타입을 선언부에서 매개변수화 하고 사용할 때 구체화 하는 겁니다.

> 내가 그의 이름을 불러주기 전에는
그는 다만
하나의 몸짓에 지나지 않았다.

> 내가 그의 이름을 불러주었을 때,
그는 나에게로 와서
꽃이 되었다.

```java
// 선언부에서 T로 매개변수화
public class JBox<T> implements Box<T> {

    protected T element;
    public JBox(T element) {
        this.element = element;
    }

    public T get() {
        return this.element;
    }

}
// 쓸 때는 Cat으로 구체화
JBox<Cat> catBox = new JBox<Cat>(new Cat());
```
 
## 왜 제네릭?

이전에 제네릭이 없을 때 문제, 그리고 제네릭을 어떻게 쓰는지 봤으니 코드를 직접 비교해보면 좋을 것 같습니다.


```java
    List list = new ArrayList();
    list.add("hello");
    String s = (String) list.get(0);

    List<String> list = new ArrayList<String>();
    list.add("hello");
    String s = list.get(0);   // no cast
```

## *generic is compile time thing!*

명심해야 할 것은 제네릭은 **컴파일 타임** 에만 적용된다는 것입니다.

```java
public class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```

위와 같이 작성해도 컴파일이 되고 나면 아래와 같이 변경됩니다. 
제네릭은 어디까지나 컴파일 타임에 type safe하게 해주는 것이지, 런타임에 뭘 해주거나 하지는 않습니다. 이점을 꼭 명심해야 나중에 이상한 짓을 하지 않습니다.
이렇게 타입이 런타임에 사라지는 것을 type erasure라고 부릅니다.
```java
public class Node {

    private Object data;
    private Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() { return data; }
    // ...
}
```

## generic의 상한(upper bound)

그냥 아무거나 되는 매개변수 타입에 제약을 걸 수도 있습니다. 

```java
    //아래와 같은 클래스를 선언할 경우 반드시 구체화할 때 Number를 상속받아야 합니다.
    public class Node<T extends Number> {
    // ...
}

    Node<String> stringNode; // error!!!!!
```

```java
    //두개 이상의 상한을 가질 수 있습니다.
    public class Node<T extends Number & Comparable<T>> {
    
    // ...
}
```



## 제네릭 클래스의 상속 관계

이게 상당히 헷갈리는 주제입니다.
그림처럼 제네릭 클래스의 매개변수 타입이 상속관계라고 해당 
제네릭 클래스 타입간의 상속관계가 유지되는 게 아닙니다. (정수는 숫자이지만, 정수를 담는 박스는 숫자를 담는 박스가 아니라는 거죠.)
![img.png](img.png)
![img_1.png](img_1.png)

이게 왜 이런지 제대로 이해하시려면 우선 상속관계... 즉, **subtype** 이 뭔지 아셔야 합니다.

## SubType?

<details>
    <summary>
    리스코프 치환 법칙
</summary>

X2가 X1의 서브타입일 때
    모든 프로그램 P에서 
X1의 인스턴스를 
X2 인스턴스로 변경했을 때도 올바르게 동작한다.
</details>

이런 뜻입니다. 엄밀한 의미에서 서브타입을 만족하기 위해서 [여러가지 조건](https://ko.wikipedia.org/wiki/%EB%A6%AC%EC%8A%A4%EC%BD%94%ED%94%84_%EC%B9%98%ED%99%98_%EC%9B%90%EC%B9%99) 이 있습니다만 , 
선행되어야 하는 조건은
1. 메서드 인수의 반공변성
2. 반환형의 공변성

입니다. 이게 뭔 소리냐고요? 우선 1번부터 설명하겠습니다. 
쉽게 설명하면 X2가 X1의 subType이 되기 위해서는
X2의 메서드의 인자들에 대응하는 X1의 메서드 인자들이 SuperType 이어야 한다는 것입니다.
제가 얘기하고도 무슨 소리인지 모르겠군요... 
다시 설명해보면 화살표가 subType에서 superType을 가리킨다고 했을 때(예 : String -> Object) 해당 클래스 사이에 정의한 화살표 방향과, 
메서드의 모든 인자들의 화살표 방향이 반대이면 반공변성을 만족시킵니다.
예를 들어보겠습니다. 

```java
// 설명을 위한 pseudo code입니다. 
// childContainer와 SuperContainer 사이에 type 관계를 맺으려면 명시해줘야 합니다.
public class SuperContainer extends Container<String> {
    private String element;

    public SuperContainer() { }
    
    public void setElement(String element) {
        this.element = element;
    }
}

public class ChildContainer extends Container<Object> {
    private Object element;

    public ChildContainer() { }

    public void setElement(Object element) {
        this.element = element;
    }
}

```

슈퍼컨테이너 클래스와 차일드컨테이너 클래스 모두 setElement라는 메서드를 가지고 있습니다. 
슈퍼컨테이너은 string 인스턴스을 인자로 받고, 차일드컨테이너은 Object 인스턴스를 인자로 받습니다.
Object는 String의 SuperType이라고 배웠습니다.
차일드컨테이너를 슈퍼컨테이너의 subType이라고 가정했을 때 차일드컨테이너는 슈퍼 컨테이너의 메서드 인수의 반공변성을 만족시킨다고 할 수 있습니다. 
(슈퍼컨테이너 <- 차일드컨테이너, 메서드의 인자인 String -> Object, 위에서 얘기한 것처럼 반대입니다.)

이제 2번을 설명하겠습니다. 
2는 클래스의 메서드가 반환하는 결과의 관계가 클래스 사이의 관계와 유지되어야 한다는 겁니다. B클래스가 A클래스의 SubType일 경우 
B클래스의 메서드가 반환하는 모든 결과값이 그에 상응하는 A 클래스의 메서드의 결과값의 subType이면 된다는 겁니다. 
다시 예를 보겠습니다.

```java
// 설명을 위한 pseudo code입니다. 
// childContainer와 SuperContainer 사이에 type 관계를 맺으려면 명시해줘야 합니다.
public class SuperContainer extends Container<Object> {
    private Object element;

    public SuperContainer(Object element) {  this.element = element; }
    
    public Object getElement() {
        return this.element;
    }
}

public class ChildContainer extends Container<String> {
    private String element;

    public ChildContainer(String element) { this.element = element; }

    public String getElement() {
        return this.element;
    }
}

```

여기서는 아까 예제와 다르게 SuperContainer가 `Container<Object>`를 상속하고, ChildContainer가 `Container<String>`을 상속합니다. 
둘 다 getElement가 있는데, SuperContainer는 Object를 반환하고, ChildContainer는 String을 반환합니다. 
그러면 ChildContainer를 SuperContainer의 SubType이라고 가정했을 때 반환값에 대한 공변성 관계가 만족이 됩니다. 
ChildContainer -> SuperContainer 그리고 String -> Object

이해가 되셨나요...? 이해가 안되었다면... 피드백을 주시면 보완하도록 하겠습니다. 

다시 정리하면, 클래스 B가 A의 SubType이려면 모든 메서드의 인자의 타입에 대해서는 SuperType이어야 하고,
반환값에 대해서는 SubType이어야 한다는 게 필요조건입니다. 

그런데, 이러한 사실을 아는 것보다 **왜 그런지** 직관적으로 이해하는 게 더 중요합니다. 

우선 아래와 같은 클래스들이 있다고 해볼게요. 

```java
public class Super {
    private Object element;

    public Super() {

    }

    public Object getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
```

Super는 그냥 String을 받아서 저장했다가 Object를 반환할 수 있는 클래스입니다. 
제가 만약 Super라는 클래스의 인스턴스 superInstance를 쓰고 있었다면, 
```java
Object o = superInstance.getElement();
//혹은
superInstance.setElement("제네릭");
```
밖에 안쓰겠죠. 제가 만약 Sub라는 클래스를 만들어서 Super의 subType으로 만들고 싶다면 
```Object o = superInstnace.getElement();```
```superInstance.setElement("제네릭");```
에 해당하는 코드에 Sub의 인스턴스를 갈아끼워넣어도 컴파일러를 만족시켜야 합니다. 
왜냐하면 **부모 타입 변수에 subtype 클래스 인스턴스를 할당할 수 있다.** 라는 기본 전제가 있기 때문입니다.
따라서, subtype으로서 동작하려면
- Object 타입의 변수에 할당을 만족시키려면 getElement 함수는 Object 타입을 상속한 클래스를 반환해야 하고 (반환값의 공변성)
- setElement로 String을 받을 수 있는 조건은 String의 SuperType을 받을 수 있어야 합니다. (인자의 반공변성)

를 만족시켜야 하는 겁니다.

아까 Integer가 Number의 SubType일 때 , `Box<Integer>`가 `Box<Number>`의 subType이 아니다 라는 사진이 있었는데 왜 그런지 살펴보겠습니다. 
조금 내용을 바꿔서 Odd, Event 이라는 Number의 SubType으로 예를 들겠습니다.

```java

class Box<T> {
    private T element;
    public Box () { }
    public T get() {
        return this.element;
    }
    public void set(T element) {
        this.element = element;
    }
}

Box<Odd> oddbox = new Box<Odd>();
// 만약 Box<Number>가 Box<Odd>의 SuperType이라면... 
Box<Number> numberBox = oddbox;
//상기과 같은 할당을 만족한다. Box<Number>가 쓰일 수 있는 곳이면 Box<Odd> 모두 쓰일 수 있기 때문에... 
numberBox.set(new Even(8));
// numberbox의 set함수는 Number class의 인스턴스를 인자로 받고, 짝수는 Number이기 때문에 호출이 가능하다.
Odd odd = oddbox.get();
// !!! 짝수인데, 홀수에 할당이 되어버렸다.


```

사실 저 마지막 줄 코드인 홀수 할당을 통해 오류를 검증하지 않아도, 
`numberBox.set(new Even(8))`을 `Box<Odd>`는 수행할 수 없기 때문에 SubType이라고 할 수 없습니다. 

## 필요 조건일 뿐... 

인자의 반공변성 만족과 반환값의 공변성 만족은 subType을 정의하는 데 필요한 필요 조건일 뿐입니다. 
저 조건을 통과하면 컴파일러는 오류를 뱉지 않습니다. 
컴파일러는 최소조건만 체크합니다. 


## 결론은? 

결국 `Box<Number>` 변수에 `Box<Odd>`를 할당할 수 없습니다. 

대신 wildcard라는 다른 방법이 있습니다. 


wildcard 는 ? 로 표기하고요. *뭔지 모르겠지만...* 으로 치환된다고 생각하시면 됩니다.  


Box<?> => 뭔지 모르겠지만 type이 닮긴 박스

Box<? extends Cat>

뭔지 모르겠지만 고양이 subtype이 닮긴 박스 

Box<? super AfricaLion>

뭔지 모르겠지만 subtype이 africaLion인 superType이 닮긴 박스

로 치환된다고 보시면 됩니다.

```java
    //AfricaLion -> Lion -> Cat 이 상속관계일 때... 
    Box<Lion> lionBox = new Box<>(new Lion());
    //1. 사자 타입인스턴스를 담은 박스는 뭔지 모르겠는 type이 닮긴 박스의 하위 타입입니다.
    Box<?> noIdeaBox = lionBox;
    //2. 사자 타입인스턴스를 담은 박스는 뭔지 모르겠지만 고양이 subtype이 닮긴 박스의 하위 타입입니다.
    Box<? extends Cat> catBox = lionBox;
    //3. 사자 타입인스턴스를 담은 박스는 뭔지 모르겠지만 아프리카 사자의 superType이 닮긴 박스의 하위 타입입니다.
    Box<? super AfricaLion> africaBox = lionBox;
    
    //위에 1,2,3 모두 만족합니다.
```

검증을 해봅시다. `noIdeaBox` 부터 생각해볼게요. 얘는 뭐가 뭔지 모르는 type이 닮긴 박스입니다. 우리는 이 type에 대해서 아는 게 없습니다. 
`noIdeaBox.get()` 은 무슨 타입인지 모르기 때문에 오로지 Object 변수에만 할당할 수 있습니다. (그 어떤 타입도 Object에는 할당이 가능합니다.) `noIdeaBox.set`은 쓸 수 조차 없습니다. 무슨 타입인지 알 수가 없으니까요.
lionBox의 get은 Lion을 반환하기 때문에 `noIdeaBox.get()`이 쓰이는 곳에 넣어도 문제가 없습니다.

다음으로 가보겠습니다. `catBox` 는 get 함수가 `? extends Cat`을 반환합니다. set은 `? extends Cat`을 인자로 받고요. 
뭔지 모르겠지만 Cat을 상속하는 타입은 우선 Cat 타입 변수에 지정이 가능합니다. set은 쓸 수가 없습니다. ?가 뭔지 모르니까요. 
set의 인자로 어느 type이 적절한지 모른다는 뜻입니다. `Cat cat = catBox.get();` 밖에 못씁니다. 
해당 코드에 lionBox를 대입하면 `lionBox.get()`은 Lion 객체가 나올테고, 이는 Cat 타입 변수에 할당이 가능합니다. 

'africaBox'는 get 함수의 반환값을 만족시킬 타입이 아프리카 라이온의 superType이라는 것 밖에 모르기 때문에 
object, cat, lion 중 하나인 상황이라 object 타입 변수에 밖에 할당할 수 없습니다. 
set 함수의 경우에는 인자로 object, cat, lion 중에 하나이라 AfricaLion을 넣으면 그 어떤 경우에도 만족시킵니다.
`Object object = africaBox.get()` `africaBox.set(new AfricaLion());` 으로 쓸 수 있습니다. 
africaBox에 lionBox를 대입할 경우, lionBox의 get함수 반환값은 lion type인데 이는 object 타입의 subtype이니까 object type 변수에 할당할 수 있고, 
lionBox.set 함수는 Lion을 인자로 받으니까 AfricaLion을 인자로 넣어도 동작할 수 있다는 점에서 
`Box<Lion>` 이 `Box<? super AfricaLion>`의 subtype일 수 있다는 걸 확인했습니다. 

어? 선언부에 get set만 있는 건 아니자나? 라고 생각하시는 분들도 있을 겁니다. 하지만, 매개변수 타입 `<T>`는 어떻게 해도 함수의 인자나 반환값과 관련이 있게 됩니다.
그리고 반드시 T 타입을 직접 반환하는 게 아닌 매개변수 T를 쓰는 또 다른 제네릭 타입을 반환하더라도 마찬가지입니다. 따라서, get set만 놓고 생각해봐도 큰 문제는 없습니다.
아직도 납득이 안가시는 분은 수학과를 가시길 바랍니다..:) 

? 을 쓰는 거에 대해서 정리를 다시 하자면, 
`Box<? extends Cat>` 이라고 했을 경우는 get 함수를 Cat에 할당할 수 있었고, 
`Box<? super AfricaLion>` 이라고 했을 경우는 set 함수에 인자로 AfricaLion을 할당할 수 있었습니다.
extends 를 쓰는 상한은 매개변수 타입의 인스턴스를 반환하는 생산성 함수만 쓸 수 있고,
super 를 쓰는 하한은 매개변수 타입의 인스턴스를 인자로 받는 소비성 함수에서만 쓸 수 있습니다.
Effective Java에서는 외우기 쉽게 이걸 PECS라고 명명했습니다. PRODUCER-EXTENDS, CONSUMER-SUPER 관계인 거죠.
저도 처음에는 저 공식만 외웠는데, 제네릭이 모든 언어마다 기능이나 제약이 똑같은 게 아니라서 결국 하위타입의 경우
반환값에 대한 공변성과, 인자로 반공변성을 만족시켜야만 한다라는 사실을 직관적으로 받아들이지 못하면, 고생하게 되더군요.

제가 사실 ~~의도적으로~~ 빼먹은 게 한가지 있습니다. 인자로 T 타입을 받아서 T에 대해서 반환하는 함수입니다. 
이는 wildcard로 된 제네릭타입 변수에 할당하는 순간 해당 함수는 쓸 수 없게 됩니다. 이 부분은 읽으신 분들이 생각해보시면 좋을 것 같습니다.

이외에도 알아야 할 부분들이 있습니다. [제네릭 제약 사항](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html) 이나 [bridge method](https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html) 가 예입니다. 
개인적으로는 직관적으로 generic 타입의 상속 관계를 직관적으로 파악하는 게 훨씬 중요하다고 생각해서 해당 부분만 tmt 해버렸네요.

읽어주셔서 감사합니다.