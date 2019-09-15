# 리스코프 치환원칙

묻지마~ 니가 알고 있던 걔가 아니야. 그 아저씨는 변했어.

묻지말고 무조건 시켜. 시키면 그 object가 알아서 할거야.



![image-20190914173620193](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4tus5oyj32480u0n07.jpg)



a,b,c 가 동일한 함수가 있다면 우리는 abstract(a,b,c) 를 만들텐데, 이 때 ***성급한 추상화를 할 수 밖에 없다.***



<img src="https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4vhtc69j31c60s8q52.jpg" alt="image-20190914173750457" style="zoom:67%;" />



이런 행위를 하게 된다. 잘못된 context 버그를 만들어 낸다.



![image-20190914173842619](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4w8i6ynj31eo0qawgi.jpg)



이렇게 되면 어떻게 할꺼냐? 우리는 d() 를 쓰고 싶은데 어떻게 할거냐? 바로 문제가 발생하는 시발점.



이 상태에서 

![image-20190914173934375](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4x51bxjj30pm0emmxv.jpg)



 이렇게 되면 업캐스팅이 아닌, 다운캐스팅으로 되어 버린다. 이게 OOP에서 발생하는 문제이다.



## 이 문제를 해결하기 위해 우리는 Generic을 활용할 것이다.

![image-20190914174035132](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4y6q028j311w0e00tn.jpg)





# 이제 우리는 실세계 예제를 통해 문제를 풀어보자.

![image-20190914174150427](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z4zifqw2j311g0i4ab3.jpg)





기획자는 어디서 기획서(paper) 를 받음. 그 기획서에 사양서가 적혀져있을 것이야.

 먼저 Paper - 서버가 필요한 ServerClient, 서버가 필요없는 Client 를 보고 Programmer를 고용할 것입니다.

그리고 그 Programmer는 FrontEnd와 Backend가 있다.



형으로 추상하기 위한 `Paper`

![image-20190914174258366](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z50o7c8ij319q0e675v.jpg)



`Programmer는 Paper를 보고 makeProgram을 할 것이다.`



![image-20190914174359175](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z51qd3rnj319s0e4dit.jpg)



Paper를 통해 구현을 하면 위와같은 코드가 될 것이다.

 

나중에 기획서에 더 많은 내용이 들어가서 추가될 것.



ServerClient는 아래와 같다.

![image-20190914175825376](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5gr6vuxj31a00g2n1y.jpg)



![image-20190914175944394](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5i58o40j319w0iin1m.jpg)

![image-20190914180120188](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5jsi877j319s0imdki.jpg)

여기서 문제 발생 paper가 다운캐스팅을 시도하고 있다. 그러면 이 클래스는 확장에 막혀있게 된다. 이런 문제를 해결하기 위해서는 코드로서 문제를 해결하기 위한 방법을 모색해야 한다.



***가장 무서운 If***

왜냐? goto를 일으켜서 런타임, 컴파일에러를 일으키지 않아- 

그래서 if나 swich를 쓰면 정적타입의 안정성을 잃어버리게 된다.

그럼 모든 if를 제거하는게 정답인가? 사실 `가능하면` 이다. 

![image-20190914180741532](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5qf9nf9j319w0lqjyd.jpg)

 위코드를 보면 또 ServerClient가 다운캐스팅을 시도하고 있다.



위같은 코드들은 특정 상황에서만 문제를 일으키기 때문에 문제가 발생한다. 



![image-20190914181557992](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5z0p6n9j312m0g6t9k.jpg)



![image-20190914181608194](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z5z67qpkj31a20iktd7.jpg)

`LSP가 일어나는 부분은 위와 같다.`



LSP가 깨지면 OCP가 무조건 성립되지 않는다. 그럼 위 문제를 어떻게 해결해야 될까?



바로 `Don't Ask` 행위를 해야만 한다.

![image-20190914181755393](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z611zp0rj31a40imgqd.jpg)

***위 코드는 papger가 Client인지 물어보고 있다. 이건 헐리웃 윈칙을 위반하고 있다.***



그럼 어떻게?

![image-20190914181845132](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z61wcnq4j319s0j8793.jpg)



"니가 나한테 데이터를 Set해줘." 라고 물어보고 있음. 이렇게 되면 나를 주고 끝났음.

여기서 `this` 는 나 자신을 보내버린다. 

![image-20190914182038884](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z63w597fj319y0i6n4b.jpg)

 이렇게되면 Backend는 OCP를 지키게된다. 이렇게 되면 `Programmer` 를 추상클래스로 변경할 수 있어야 한다. 이렇게되면 템플릿 메서드 패턴으로 변경되면서 자유자재로 바꿀 수 있어야 한다.



다시 Paper로 돌아가자.

![image-20190914182441428](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z682o1b7j31a00eiabd.jpg)



이렇게 바꿔도 사실 문제는 변하지 않는다.

![image-20190914182607926](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z69owdo3j319s0le79j.jpg)

![image-20190914182622573](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z69u1cg4j319q0kcafw.jpg)

아무리 모델링을 잘해도 실제 구현은 다운캐스팅을 할 수 밖에 없다.

가장 좋은 인터페이스는 함수가 하나도 없는게 좋다. 왜냐하면, 많을 수록 다른점이 많다는 것을 의미하기 때문이다. LSP, OCP를 해결하기 위해 핑퐁을 하지만 여전히 한계가 보인다.



그리고 if가 하나인 첫번째 코드와 2개의 두번째 코드의 의미가 다르다는걸 알아야 한다.

그럼 어떻게 해야 될까?



if가 2개인 코드가 좀 더 다양하다는 사실을 알아야 한다.



*cf) 만약 다운캐스킹을 하게 된다면 누구한테 위임하는지에 대해 어떤 의미를 갖는지 알아야 한다.*



![image-20190914185500252](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z73qn8l7j30rw0kqaej.jpg)

이런 다운캐스팅은 코더에게 잘못된 코드를 짜게할 수 있는 유도를 유발한다.



위 문제를 해소하기 위해서 `Generic을 사용한다.`

추상형을 유지하면서 구상형을 클라이언트가 결정할 수 있도록 도와준다.

![image-20190914190027836](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z79c752tj31a20kwn2i.jpg)



그래서 `instanceof` 이 발견된다면 Generic을 쓰기 위한 행위를 취해야 한다.

T를 정의할 때 업캐스팅으로 한다. 그럼 LSP, OCP를 해결한다고 볼 수 있다.

![image-20190914190323972](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z7cdpjwtj31ag0iqdkp.jpg)



`Generic`은 `instanceOf`를 제거할 수 있는 기회을 만들어 준다.

 클래스를 선언하는 과정에서 하위형을 결정했기 때문에-

Client는 확 줄어든 범위내에서만 코드를 짤 수 있게 된다. 

`instanceOf` 가 보이면 기계적으로 generic을 쓸 수 있도록 해서 LSP, OCP를 어기지 않도록 해야 한다.



> if 없애기 위해서는 그 만큼의 경우의수 만큼 만들어야 하고 그 선택은 클라이언트에게 맡긴다.



그렇지만 문제는 남아있다.

![image-20190914190902932](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z7i9g8vxj319w0jw7aq.jpg)

##  ServerClient는 2개의 instanceOf 가졌기 때문에 generic으로 바로 수정하기 어렵다. 이때는 어떻게 할까?

![image-20190914191001503](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z7j95rg1j317k0gcq40.jpg)

 이 때 할리웃 원칙을 다시 생각하자.



자식이 부모를 알게되면 사용되는 함수를 줄일 수 있다.

![image-20190914191159869](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z7lbp78sj31a00gmjuf.jpg)

![image-20190914191822642](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z7ryiuuxj31a60is0xr.jpg)

다시 돌아가자.

Paper가 아무런 인터페이스로 돌아가서 다시 판단하자.





![image-20190914192839003](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z82pt98gj319o0g8q5m.jpg)



T의 처리를 위임.

`Dont ask Tell` 

알아서 Tell 해!`setDate(paper)`



![image-20190914193029581](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z84kc4d9j31ac0hugq2.jpg)



if를 제거하는 만큼의 형을 만들어내야 되고 그 형을 만드는데 중간에는 Generic이 존재하는 이유 이다.

setData의 구현을 밀어내기 위해서 Backend 클래스를 abstract로 만들어서 구현을 밀어냈다.



![image-20190914193342787](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z87x1aj3j319o0ikn1l.jpg)



여기까지 생각해보면 PM이나 Director를 괴롭혀야 한다는 사실을 이해할 수 있다. 실세계에서도 마찬가지이다. 



![image-20190914193535052](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z89upndbj30z80ggt9d.jpg) 







![image-20190914193548162](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8a6ciwpj318o0nkn3w.jpg)



위 코드는 범용 Paper를 받아 들이는 코드이다. 

그러나 만약 Programmer를 아래와 같이하면 역할이 분명해지는데, 아래와 같이 변한다.

이전에는 Program또는 Paper에 존재하던 것을 변경할 수 있었다.



![image-20190914193715022](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8blh8v6j319y0kqjxs.jpg)



지금까지 잘 보면 클라이언트로 계속 역할의 결정을 밀어내는 것이다.

![image-20190914194018435](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8ertfptj31ae0hctcz.jpg)



이번에 맨 첫번째줄의 `instanceof`는 어떻게 해결해야되나? Director

![image-20190914194159389](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8gj7xgrj313s0u0wnr.jpg)



![image-20190914194225873](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8gz9kx5j315q0l4grg.jpg)



![image-20190914194309547](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8hqhsnkj319c0lc7bw.jpg)



그럼 여기서는 Paper 에 run 배열을 만들면 이 문제를 해결할 수 있다. 

![image-20190914194354364](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8iiru3dj31ae0gqdl1.jpg)



![image-20190914194414873](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8iv0rzdj319u0a6tcn.jpg)



Main까지 결정을 미뤘다. 이렇게되면 DI를 통해서 의존성을 주입해 문제를 해결할 수 있다. 

![image-20190914194716560](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8m1uso6j30z80jsq72.jpg)

여기에 setProgrammer는 더이상 필요하지 않게 되었다. 

![image-20190914194938615](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8ohrv5gj30va0jsjvf.jpg)



![image-20190914195051638](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8pqta8uj31ac0jo453.jpg)



`protected 수준으로 올릴 수 있다.`



![image-20190914195242924](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8roumr1j315g0jctdl.jpg)



![image-20190914195259736](https://tva1.sinaimg.cn/large/006y8mN6ly1g6z8ry7jpwj319u0iw7ck.jpg)

