# 이책에서 말하는 라이브러리와 프레임워크의 차이는?



저자는 라이브러리와 프레임워크차이에 대해서 

`제어 역전`  여부에 따라 중요하게 여긴다.



**만약 우리 코드에 제어 역전이 없다라면 누가 갖고 있는걸까?**



제어역전이 존재한다면 대부분의 라이프사이클에 맞쳐서 `프레임워크`에 그저 공급만하게 된다.

`라이브러리`는 우리가 언제 실행할지 결정할 수 있으나, `프레임워크`는 결정할 수 없다.

안드로이드, 스프링을 떠올리자.



`안드로이드 라이프사이클에 코드를 정의하여 제어를 역전한다. 이는 제어 타운을 안드로이드 프레임워크가 가진다.`



일반적인 `전략 패턴`과 `데코레이터 패턴` 사이의 컴퍼짓 패턴이 존재한다.



`컴퍼짓 패턴`은 폴더와 디렉토리 관계를 말한다.

폴더와 파일이 공통되게 응답하게 만든다.



그래서 대부분의 패턴을 학습하는 과정이 동일하다.

- 전략 패턴
- 컴퍼짓트 패턴
- 데코레이터 - 문제는 중간에 끊을 수 없어 Chain of Responsiblity 패턴 사용
- Chain of Responsiblity 
- composite pattern
- command pattern



전략 패턴에 전략패턴을 쓰게 되면 어탭터 패턴을 쓴다.

합성과 상속 그 자체가 템플릿/전략 패턴을 의미한다.

command 패턴이 어려운 이유는 지연 평가를 실행시키기 때문이다.



**그래서, 오늘 Composite 와 Visit Pattern을 학습하자.**

- 패턴을 익히기 위해서는 역할상 몃개를 가졌는지 알고 있어야 한다.
- 그래서 처음 디자인 패턴을 학습하기 위해서는 외워야 한다.





## Todo 를 통해 실전으로 실행해보자.

```java
public class Task {

    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;

    Task(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Boolean getComplete() {
        return isComplete;
    }
}
```



각 필드를 final 하지 않은 이유는 변경 가능하기 때문에 그렇다.

`toggle`은 `public`으로 만든 이유는 외부에서 관여할 수 있는 유일한 메서드 이기 때문이다.



이제 Task를 만들었고, 이를 담을 그릇을 만들어야 한다.



```java
public class Tasks {

    private String title;
    private final Set<Task> list = new HashSet<>();

    public Tasks(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addTask(String title, LocalDateTime date) {
        list.add(new Task(title, date));
    }

    public void removeTask(Task task) {
        //메모리 주소로 식별된당.
        list.remove(task);
    }

    public List<Task> getList(SortType type) {
        List<Task> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        tasks.sort((a, b) -> type.compare(a, b));
        return tasks;
    }
```

많은 회사들이 `setTitle` 과 `Tasks` 생성자 둘에 위임하는 게 아닌, 한 곳에서만 동작되도록 수정해야 한다. 

이런 행위를 하지 않으면 많은 에러를 발생시킨다.

```java
    public Tasks(String title) {
        setTitle(title);
    }
```

`addTask`  / `removeTask` 에 `Task` 을 공개하지 않고, 최대한 뒤로 밀었다.  왜냐하면 `Task` 의 생성을 외부에 유출하게 되면 `Task` 의 수정에 많은 비용이 발생하게 된다.

```java
    public void addTask(String title, LocalDateTime date) {
        list.add(new Task(title, date));
    }

    public void removeTask(Task task) {
        //메모리 주소로 식별된당.
        list.remove(task);
    }
```

그래서 위와 같이 `title`, `date` 를 받도록 하자. 그 후 다시 `Task 생성자` 로 돌아가서 public 을 default 생성자로 변경하자. 

Task의 수정은 오직 Tasks까지만 여파가 흘러간다.



>  접근제어자 public VS default 차이점을 이해하자.  

우리는 list를 외부에 오픈할 필요가 있다. 외부에 오픈하게 될 때는 본인의 필드는 오직 본인이 해야 한다. 그러므로, 외부에 오픈할 때는 사본을 넘겨줄 필요가 있다.



여기서 `깊은 복사`와 `얊은 복사`를 결정해야 한다.


```java
    public List<Task> getList(SortType type) {
        List<Task> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        tasks.sort((a, b) -> type.compare(a, b));
        return tasks;
    }
```

> 깊은 복사 VS 얊은 복사

여기서 `SortType` 을 결정해야 한다.

```java
public enum SortType {
    TITLE_DESC {
        @Override
        int compare(Task a, Task b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(Task a, Task b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(Task a, Task b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    TITLE_ASC {
        @Override
        int compare(Task a, Task b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    };

    abstract int compare(Task a, Task b);
}
```



`객체지향에서는 위임한다.`  이 말이 중요하다. ` tasks.sort((a, b) -> type.compare(a, b));` 을 중요하게 생각하자.

언어차원에서 `static` 초기화 하기 전에 `enum` 인스턴스 객체를 내가 원하는 갯수만큼 만들어 준다. 스레드를 실행하기 전에 만들어 주기 때문에 `enum`을 활용하는게 스레드 세이프하다.

`enum` 은 결과적으로 추상클래스이다.



***결과적으로 순식간에 4개의 전략패턴을 가지게 되었다.***



- 클래스를 만드는건 N개 이상이라고 확신할 때 만들기 때문에, 함부로 만들면 안된다.
- 우리는 인스턴스를 생성해야 되는 상황에 클래스를 통해 만들지,  enum을 통해 만들지 확인해볼 필요가 있다.



이번에는 한 클래스안에 Composite 패턴을 구현해보자. 이때 4가지의 등장인물이 등장한다.

```java
public class CompositeTask {

    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;
    private final Set<CompositeTask> list = new HashSet<>();

    public CompositeTask(String title, LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void addTask(String title, LocalDateTime date) {
        list.add(new CompositeTask(title, date));
    }

    public void addRemove(CompositeTask task) {
        //메모리 주소로 식별된당.
        list.remove(task);
    }

    public TaskReport getReport(CompositeSortType type) {
//        List<CompositeTask> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        // 문제 발생!!!!!
//        tasks.sort((a, b) -> type.compare(a, b));
        TaskReport report = new TaskReport(this);
        for (CompositeTask t : list) {
            report.add(t.getReport(type));
        }
        return report;
    }

    public boolean getIsComplete(){
        return isComplete;
    }
}
```

위 코드는 `Task`  와 `Tasks` 을 합친 것과 같다.



그래서 지금  `leaf` 이자 `folder` 로 변신하게 되었다. `List` 안에  `List`가 들어 갈 수 있다.

```java
public List<CompositeTask> getList(SortType type) {
        List<CompositeTask> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        tasks.sort((a, b) -> type.compare(a, b));
        return tasks;
    }
```

우리가 Return 해야 하는 List는 어떤 list 일까?

 이렇게 만들었지만, 나의 사본을 만들어서 리턴해야 하지만 만들 수 없다. 나의 사본을 만들기 위해서 우리는 `TaskReport` 를 만들자.



그럼 `TaskReport` 에는 무엇이 필요할까?

```java
public class TaskReport {

    private final CompositeTask task; // tree
    private final List<TaskReport> list = new ArrayList<>(); //leaf

    public TaskReport(CompositeTask task) {
        this.task = task;
    }

    public void add(TaskReport report) {
        list.add(report);
    }

    public CompositeTask getTask() {
        return task;
    }

    public List<TaskReport> getList() {
        return list;
    }
}
```



이게 바로 `Composite Pattern` 이다. 



그렇다면 `CompositeTask`  에서는 어떻게 `getList` 해야 될까?

이제부터는 TaskReport를 리턴해야 할 것이다. 

코드는 다음과 같이 변할 것이다.

```java
    public TaskReport getReport(CompositeSortType type) {
        TaskReport report = new TaskReport(this);
        for (CompositeTask t : list) {
            report.add(t.getReport(type));
        }
        return report;
    }
```

***이 코드가 굉장히 중요하다.***



 Composite 패턴에 나오는 등장인물이 모두 나왔다.



여기서 T 즉 제네릭을 활용하면 명확하게 사용할 수 있지만, 자바에서는 enum을 클래스로 취급하지 않기 때문에- 추상 클래스을 만들어서 4개의 인스턴스를 만드는 것이다.



레거시를 안고가는 자바는 어쩔 수 없다.



`이제 Main으로 돌아가보자.`



```java
public class main {

    public static void main(String[] args) {

        CompositeTask root = new CompositeTask("Root", LocalDateTime.now());
        root.addTask("sub1", LocalDateTime.now());
        root.addTask("sub2", LocalDateTime.now());

        TaskReport report = root.getReport(CompositeSortType.TITLE_ASC);
        List<TaskReport> list = report.getList();
        CompositeTask sub1 = list.get(0).getTask();
        CompositeTask sub2 = list.get(1).getTask();

        sub1.addTask("sub1_1", LocalDateTime.now());
        sub1.addTask("sub1_2", LocalDateTime.now());
      
				sub2.addTask("sub2_1", LocalDateTime.now());
        sub2.addTask("sub2_2", LocalDateTime.now());
      
```



ㅇㅕ기까지 보면 트리구조를 갖게 된다는 사실을 알 수 있다. 

그러나  이걸 확인하고 싶다면 우린 renderer을 만들 필요가 있다.



이 컴포짓을 순환할 수 있는 어떤 걸 만들어야 한다.



순회는 제어 구조. 즉, 라이프사이클을 타게 될 것이다.



그래서 이번에는 `Visitor 패턴`을 이해해보자.



동적 순회 트리를 짜는 것이 어렵다 = 우리는 코드와 친숙하지 않다.

```java
public class Renderer {

    private final Supplier<Visitor> factory;

    public Renderer(Supplier<Visitor> factory) {
        this.factory = factory;
    }

    public void render(TaskReport report) {
        render(factory.get(), report, 0);
        //그림을 그려주는 애한테 Depth를 줘야한다.
    }

    private void render(Visitor visitor, TaskReport report, int depth) {
        //라이프싸이클을 타는 비지터가 하게 한다.
        visitor.drawTask(report.getTask(), depth);

        for (TaskReport r : report.getList()) {
            render(visitor, r, depth + 1);
        }

        visitor.end(depth);
    }
}
```



인자 2개씩만 받는 함수 6개를 만드는것이 중요.



```java
private final Supplier<Visitor> factory;

public Renderer(Supplier<Visitor> factory) {
  this.factory = factory;
}
```

객체가 아닌, 함수로 받아들여야 범용적으로 활용할 수 있다.

```java
Renderer renderer2 = new Renderer(() -> new JsonVisitor());
```



제어는 render가 다 가져갔고, render 안에는 composite 에 대한 제어가 없어졌다. 이것이 바로 프레임워크의 시작이다.



그러므로 `Visitor` 는 다음과 같은 인터페이스를 가진다.

```java
public interface Visitor {
    void drawTask(CompositeTask task, int depth);
    void end(int depth);
}
```



그럼 이번에는 `ConsoleVisitor`를 만들어 보자.

```java
public class ConsoleVisitor implements Visitor {
    @Override
    public void drawTask(CompositeTask task, int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "-";
        }
        System.out.println(padding + (task.isComplete() ? "[v] " : "[ ] "
                + task.getTitle() + "(" + task.getDate() + ")"));
    }

    @Override
    public void end(int depth) {
        System.out.println("End");
    }
}
```



이번에는 `JsonVisitor`

```java
public class JsonVisitor implements Visitor {
    @Override
    public void drawTask(CompositeTask task, int depth) {
        String padding = getPadding(depth);
        System.out.println(padding + "{");
        System.out.println(padding + "  title: \"" + task.getTitle() + "\",");
        System.out.println(padding + "  date: \"" + task.getDate() + "\",");
        System.out.println(padding + "  isComplete: \"" + task.isComplete() + ",");
        System.out.println(padding + "  sub: [ ");
    }

    @Override
    public void end(int depth) {
        String padding = getPadding(depth);
        System.out.println(padding+ "  ]");
        System.out.println(padding+ "};");
    }

    private String getPadding(int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "  ";
        }
        return padding;
    }
}
```



`console, Json Visitor` 는 제어가 배제되어있는 할일만 하는 프레임워크를 만들었다. 



프레임워크를 구성하는 Visitor 패턴이 좋은 이유는 상속구조가 얊고 기능만 상속받으면 된다. 굉장히 광범위한 상속 객체를 만들수 있다.



제어능력을 잃어버린 전략 패턴은 Visitor 패턴이라고 이야기할 수 있다.

