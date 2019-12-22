# Command Pattern

저번 시간에 배웠던 `Visitor Pattern` 를 생각하면, 제어를 놔두고, 행위에 초점을 맞춘다.



제어를 역전하는 거에는 성공했지만, 행위를 캡슐화하는 것에는 실패했다.

이런 문제를 해결하기 위해서 디자인패턴에서는 `Commnad 패턴` 이라는게 존재한다.





>  코드를 명확히 이해하기 위해서는 외우고, 따라하므로써 코드를 이해한다.

기존의 코드를 베이스로 해서 Command 패턴을 만들어 보자.



`내가 어떤 객체를 소유하고 있어.` > `이걸 Command 패턴화 하고 싶어.` 라고 생각하는게 더 편하다.



Command 패턴에 가장 기초가 되는 함수는 `execute()` 가 있다. 

- 행위를 위임한다.
- 행위뿐만 아니라, 자기만의 저장소를 가질 수 있다.

```java
public interface Command {
    void execute(CompositeTask task);
    void undo(CompositeTask task);
}
```



이제 이를 구현해보자.

일단 `CommandTask` 에 `CompositeTask` 의 기능을 위임하자.

```java
public class CommandTask {

    private final CompositeTask task;

  public CommandTask(String title, LocalDateTime date) {
        task = new CompositeTask(title, date);
    }

    public void setTitle(String title) {
					this.title = title
    }

    public void setDate(LocalDateTime date) {
					this.date = date
    }

    public String getTitle() {
        return task.getTitle();
    }

    public LocalDateTime getDate() {
        return task.getDate();
    }

    public Boolean getComplete() {
        return task.getComplete();
    }

    public void addTask(String title, LocalDateTime date) {
			list.add(new CompositeTask(title, date))
    }

    public void removeTask(CompositeTask task) {
			list.remove(task)

    }

    public TaskReport getReport(CompositeSortType type) {
        return task.getReport(type);
    }

}

```



먼저 toggle 부터 해보자.

```java
public class Toggle implements Command {
    //얘는 무슨 일??
    @Override
    public void execute(CompositeTask task) {
        task.toggle(); //행위를 객체로 만들었음.
    }

    @Override
    public void undo(CompositeTask task) {
        task.toggle();
    }
}
```

코드안에 행위로 갖고있던 것을 객체로 행위를 갖도록 해준 것.



Command 객체화 한 것

```java
public void toggle() {
	Command cmd = new Toggle(); //여기서 Toggle이 Command객체가 된다.
 	cmd.execute(task);
        //커맨드 객체화 시켰음.
}
```

행위가 객체로 빠져나옴.

```java
private void addCommand(Command cmd) {
        commands.add(cmd);
			  cmd.execute(task);
}
public void toggle() {
	addCommand(new Toggle());
}
```



이번에는 `Title` 도 객체로 주자.

```java
public void setTitle(String title) {
        addCommand(new Title(title)); //당시 Command 객체를 기억.
}
```



그리고 여기서는 `Task` 를 불변으로 봐야 한다. 그러므로 위 코드에서 `Task`  를 final로 선언하고 생성자에 다음과 같이 변경한다.

```java
task = new CompositeTask(title, date);
```

당시의 Context의 기억은 `Command` 가 기억하게 만든다.

```java
public class Title implements Command {

    private final String title; // title은 기억해야만 하는 것.
    private String oldTitle;

    public Title(String title) {
        this.title = title;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle = task.getTitle();
        task.setTitle(title);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setTitle(oldTitle);
    }
}
```

코드에는 마법이 없다. `oldTitle` 를 보면, 이전에 가졌던 데이터(oldTitle)을 Title 객체가 들고 있다.

마찬가지로 `Date`  도 변경해주자.

```java
import java.time.LocalDateTime;

public class Date implements Command {

    private final LocalDateTime localDateTime;
    private LocalDateTime oldLocalDateTime;

    public Date(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public void execute(CompositeTask task) {
        oldLocalDateTime = task.getDate();
        task.setDate(localDateTime);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setDate(oldLocalDateTime);
    }
}
```



그러므로, `CommandTask` 로 되돌아가면 다음과 같은 코드가 된다.



`addTask, removeTask` 가 남았다.

```java
public class Add implements Command {

    private final String title;
    private final LocalDateTime date;
    private CompositeTask oldTask;

    public Add(String title, LocalDateTime date) {
        this.date = date;
        this.title = title;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTask = task.addTask(title, date);
    }

    @Override
    public void undo(CompositeTask task) {
        task.removeTask(oldTask);
    }

}

public class Remove implements Command {

    private final CompositeTask baseTask;
    private String oldTitle;
    private LocalDateTime oldDate;

    public Remove(CompositeTask task) {
        this.baseTask = task;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle = task.getTitle();
        oldDate = task.getDate();
        task.removeTask(baseTask);
    }

    @Override
    public void undo(CompositeTask task) {
        task.addTask(oldTitle, oldDate);
    }
}

```

 사용하는 쪽의 코드에서는 다음과 같다.

```java
public void addTask(String title, LocalDateTime date) {
        addCommand(new Add(title, date));
    }

    public void removeTask(CompositeTask task) {
        addCommand(new Remove(task));
    }
```



`Add` 클래스에서 

```java
    @Override
    public void execute(CompositeTask task) {
        oldTask = task.addTask(title, date);
    }

    @Override
    public void undo(CompositeTask task) {
        task.removeTask(oldTask);
    }
```

oldTask 를 만들어 주기 위해 addTask 의 반환값을 task로 갖게 수정해주자.



`undo` 라는 개념은 대단한 것이 아니다. `Add` 클래스를 보면 대칭성을 갖게 된다. 

`undo`가 되려면 자연스럽게 인터페이스를 대칭성있게 만들게 된다.



`Remove class` 또한 대칭성을 가지게 된다.

```java
@Override
public void execute(CompositeTask task) {
  oldTitle = task.getTitle();
  oldDate = task.getDate();
  task.removeTask(baseTask);
}

@Override
public void undo(CompositeTask task) {
  task.addTask(oldTitle, oldDate);
}
```



궁극적으로 모든 코드는 객체로 만들어야 한다라는 궁극적인 결론에 도달한다.

 ***행위를 객체화 이것이 포인트***

하나의 예를 우리는 `for` 로 표현할 수 있다.

 

모든 행위를 객체로 만들었으니까, 비효율적으로 보일 수 있지만, 아직 `execute` 를 하지 않았기 때문에 지연실행할 수 있습니다.



- 내가 실행시키고 싶은 객체를 언제 어디서 원할 때 실행시킬 수 있다. 라는 장점을 갖게 된다.

- 순차 실행, 동기형 실행 이 일반적인 방법이라면, `Command 패턴`을 활용하면 비동기적인 방식을 갖게 된다.



일단 모든 객체를 Command 객체로 변경했다.



이제 우리는 `CommandTask`의  `undo/redo` 를 만들어 보자.

```java
public void undo() {
  if(commands.size() == 0) return;
  commands.remove(commands.size() - 1 ).undo(task)
}
```



이번에는 `redo` 를 만들고자 했는데, 과거의 상태를 가지고 있지 않기 때문에 구현할 수 없게된다.

그러므로, 우리는 `undo` 할 때 `commands.remove` 를 하면 안된다.

또한, 일반적으로 `undo` 하다가 새로운 add를 하게 된다면 현재상태의 뒷부분은 날라가게 된다.



이제 cursor를 통해 `redo` 까지 구현해보자.



**먼저 cursor의 위치를 확정하자.**

```java
private void addCommand(Command cmd) {
//        if (commands.size() > cursor + 1) {
//            commands.subList(cursor + 1, commands.size()).clear();
//        }
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }
        cmd.execute(task);
        commands.add(cmd);
        cursor = commands.size() - 1; //커서 위치 확정.
    }
```

 

다음 `undo` 는 다음과 같이 작업하자.

```java
public void undo() {
        if (cursor < 0) return;
        Command cmd = commands.get(cursor--);
        cmd.undo(task);
    }
```



> 증감연산자를 쓰지 말자?
>
> 왜냐하면, 증감연산자로 인한 실수가 많이 일어난다.



커서관리 하는 것을 일회성으로!



이제 `redo` 를 구현하면,

```java
public void redo() {
        if (cursor == commands.size() - 1) return;
        commands.get(++cursor).execute(task);
    }
```



여기서 조심해야될 부분은 `++cursor` 

다음 것을 실행해야 하므로 ++ 되어야 한다.

`addCommand` 함수에서 `cursor` 의 위치를 기억하자. 



이제 `Main`으로 돌아가보자.

```java
public class Main {

    public static void main(String[] args) {

        CommandTask root = new CommandTask("Root", LocalDateTime.now());
        root.addTask("sub1", LocalDateTime.now());
        root.addTask("sub2", LocalDateTime.now());

        Renderer renderer1 = new Renderer(() -> new ConsoleVisitor());
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        root.undo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        root.undo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));
    }
```



**Command Pattern을 왜 쓴다?**

- 객체에 메서드가 하나도 필요없게 된다.  
  : 객체가 확정되서 아웃된다.
- Command 관점에서 보면 각각의 객체들이 어탭터 패턴으로 보인다.



Composite 와 Command 패턴 의 관계 생각하기.



**Command 객체로 할 수 없는건 무엇일까?**

- undo/redo 가 될 지라도, save/load(상태 보존)가 불가능하다. 모든 행위를 기억할 수 없다.



프레임워크가 되기위해서는 총 3가지.

- Composite, 전략 패턴 을 이해해서 Visitor 패턴을 이해하자.
- Command 패턴으로 ,
- 그리고 마지막으로 데이터를 저장/로드 할 수 있어야 한다.
- 메멘토 패턴이 데이터를 저장/로드할 수 있게 해주는 마지막 키가 된다.



***이번에는 메멘토 패턴을 통해서 데이터를 저장/로드 를 구현하자.***

```java
public interface Visitor {
    void drawTask(CompositeTask task, int depth);
    void end(int depth, boolean isEnd);
}
```

end 에서 `boolean isEnd` 를 넣어줌으로써 트레일러 쉼표의 호출을 막아보자.

```java
public class Renderer {

    private final Supplier<Visitor> factory;

    public Renderer(Supplier<Visitor> factory) {
        this.factory = factory;
    }

    public void render(TaskReport report) {
        render(factory.get(), report, 0, true);
        //그림을 그려주는 애한테 Depth를 줘야한다.
    }

    private void render(Visitor visitor, TaskReport report, int depth, boolean isEnd) {
        //라이프싸이클을 타는 비지터가 하게 한다.
        visitor.drawTask(report.getTask(), depth);
        List<TaskReport> subList = report.getList();
        int i = subList.size();
        for (TaskReport r : subList) {
            render(visitor, r, depth + 1, --i == 0);
        }

        visitor.end(depth, isEnd);
    }
}
```



```java
public void save(String key) {
        JsonVisitor visitor = new JsonVisitor();
        Renderer renderer1 = new Renderer(() -> visitor);
        renderer1.render(task.getReport(CompositeSortType.TITLE_ASC));
        saved.put(key, visitor.getJson());
    }
```



load 는 숙제...



