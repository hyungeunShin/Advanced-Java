public class ThreadAndScheduling {
    /*
    스레드와 스케줄링
        멀티태스킹에서 설명한 운영체제의 스케줄링을 과정을 더 자세히 알아보자.
        CPU 코어는 1개이고 프로세스는 2개이다. 프로세스 A는 스레드 1개, 프로세스 B는 스레드가 2개 있다.
        프로세스는 실행 환경과 자원을 제공하는 컨테이너 역할을 하고 실제 CPU 를 사용해서 코드를 하나하나 실행하는 것은 스레드이다.

        프로세스 A에 있는 스레드 A1를 실행한다.
        프로세스 A에 있는 스레드 A1의 실행을 잠시 멈추고 프로세스 B에 있는 스레드 B1를 실행한다.
        프로세스 B에 있는 스레드 B1의 실행을 잠시 멈추고 같은 프로세스의 스레드 B2를 실행한다.
        이후에 프로세스 A에 있는 스레드 A2를 실행한다.
        이 과정을 반복한다.

    단일 코어 스케줄링
        운영체체가 스레드를 어떻게 스케줄링 하는지 스케줄링 관점으로 알아보자.
        운영체제는 내부에 스케줄링 큐를 가지고 있고 각각의 스레드는 스케줄링 큐에서 대기한다.

        스레드 A1, 스레드 B1, 스레드 B2가 스케줄링 큐에 대기한다.
        운영체제는 스레드 A1를 큐에서 꺼내고 CPU 를 통해 실행한다.
        이때 스레드 A1이 프로그램의 코드를 수행하고 CPU 를 통한 연산도 일어난다.
        운영체제는 스레드 A1을 잠시 멈추고 스케줄링 큐에 다시 넣는다.
        운영체제는 스레드 B1을 큐에서 꺼내고 CPU 를 통해 실행한다.
        이런 과정을 반복해서 수행한다.

    멀티 코어 스케줄링
        CPU 코어가 2개 이상이면 한 번에 더 많은 스레드를 물리적으로 진짜 동시에 실행할 수 있다.

        스레드 A1, 스레드 B1, 스레드 B2가 스케줄링 큐에 대기한다.
        스레드 A1, 스레드 B1을 병렬로 실행한다. 스레드 B2는 스케줄링 큐에 대기한다.
        스레드 A1의 수행을 잠시 멈추고 스레드 A1을 스케줄링 큐에 다시 넣는다.
        스케줄링 큐에 대기 중인 스레드 B1을 CPU 코어1에서 실행한다.
        물론 조금 있다가 CPU 코어2에서 실행중인 스레드 B2도 수행을 멈추고 스레드 스케줄링 큐에 있는 다른 스레드가 실행 될 것이다.
        이런 과정을 반복해서 수행한다.

    프로세스, 스레드와 스케줄링
        멀티태스킹과 스케줄링
            멀티태스킹이란 동시에 여러 작업을 수행하는 것을 말한다.
            이를 위해 운영체제는 스케줄링이라는 기법을 사용한다.
            스케줄링은 CPU 시간을 여러 작업에 나누어 배분하는 방법이다.

        프로세스와 스레드
            프로세스는 실행 중인 프로그램의 인스턴스이다.
            각 프로세스는 독립적인 메모리 공간을 가지며 운영체제에서 독립된 실행 단위로 취급된다.

            스레드는 프로세스 내에서 실행되는 작은 단위이다.
            여러 스레드는 하나의 프로세스 내에서 자원을 공유하며 프로세스의 코드, 데이터, 시스템 자원등을 공유한다.
            실제로 CPU 에 의해 실행되는 단위는 스레드이다.

        프로세스의 역할
            프로세스는 실행 환경을 제공한다.
            여기에는 메모리 공간, 파일 핸들, 시스템 자원(네트워크 연결) 등이 포함된다.
            이는 프로세스가 컨테이너 역할을 한다는 의미이다.
            프로세스 자체는 운영체제의 스케줄러에 의해 직접 실행되지 않으며 프로세스 내의 스레드가 실행된다.
            참고로 1개의 프로세스 안에 하나의 스레드만 실행되는 경우도 있고 1개의 프로세스 안에 여러 스레드가 실행되는 경우도 있다.
    */
}
