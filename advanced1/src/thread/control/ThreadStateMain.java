package thread.control;

import static util.MyLogger.log;

public class ThreadStateMain {
    /*
    - New(새로운 상태): 스레드가 생성되었으나 아직 시작되지 않은 상태.
    - Runnable(실행 가능 상태): 스레드가 실행 중이거나 실행될 준비가 된 상태.
    - 일시 중지 상태들(Suspended States): 자바에서 스레드의 일시 중지 상태들(Suspended States)이라는 상태는 없다. 스레드가 기다리는 상태들을 묶어서 쉽게 설명하기 위해 사용한 용어이다.
        - Blocked(차단 상태): 스레드가 동기화 락을 기다리는 상태.
        - Waiting(대기 상태): 스레드가 무기한으로 다른 스레드의 작업을 기다리는 상태.
        - Timed Waiting(시간 제한 대기 상태): 스레드가 일정 시간 동안 다른 스레드의 작업을 기다리는 상태.
    - Terminated(종료 상태): 스레드의 실행이 완료된 상태.

    1. New(새로운 상태)
        - 스레드가 생성되고 아직 시작되지 않은 상태이다.
        - 이 상태에서는 Thread 객체가 생성되지만 start() 메서드가 호출되지 않은 상태이다.
        - 예: Thread thread = new Thread(runnable);

    2. Runnable(실행 가능 상태)
        - 스레드가 실행될 준비가 된 상태이다. 이 상태에서 스레드는 실제로 CPU 에서 실행될 수 있다.
        - start() 메서드가 호출되면 스레드는 이 상태로 들어간다.
        - 예: thread.start();
        - 이 상태는 스레드가 실행될 준비가 되어 있음을 나타내며 실제로 CPU 에서 실행될 수 있는 상태이다.
          그러나 Runnable 상태에 있는 모든 스레드가 동시에 실행되는 것은 아니다.
          운영체제의 스케줄러가 각 스레드에 CPU 시간을 할당하여 실행하기 때문에 Runnable 상태에 있는 스레드는 스케줄러의 실행 대기열에 포함되어 있다가 차례로 CPU 에서 실행된다.
        - 참고로 운영체제 스케줄러의 실행 대기열에 있든 CPU 에서 실제 실행되고 있든 모두 RUNNABLE 상태이다. 자바에서 둘을 구분해서 확인할 수는 없다.
        - 보통 실행 상태라고 부른다.

    3. Blocked(차단 상태)
        - 스레드가 다른 스레드에 의해 동기화 락을 얻기 위해 기다리는 상태이다.
        - 예를 들어 synchronized 블록에 진입하기 위해 락을 얻어야 하는 경우 이 상태에 들어간다.
        - 예: synchronized (lock) { ... } 코드 블록에 진입하려고 할 때 다른 스레드가 이미 lock 의 락을 가지고 있는 경우

    4. Waiting(대기 상태)
        - 스레드가 다른 스레드의 특정 작업이 완료되기를 무기한 기다리는 상태이다.
        - wait(), join() 메서드가 호출될 때 이 상태가 된다.
        - 스레드는 다른 스레드가 notify() 또는 notifyAll() 메서드를 호출하거나 join() 이 완료될 때 까지 기다린다.
        - 예: object.wait();

    5. Timed Waiting(시간 제한 대기 상태)
        - 스레드가 특정 시간 동안 다른 스레드의 작업이 완료되기를 기다리는 상태이다.
        - sleep(long millis), wait(long timeout), join(long millis) 메서드가 호출될 때 이 상태가 된다.
        - 주어진 시간이 경과하거나 다른 스레드가 해당 스레드를 깨우면 이 상태에서 벗어난다.
        - 예: Thread.sleep(1000);

    6. Terminated(종료 상태)
        - 스레드의 실행이 완료된 상태이다.
        - 스레드가 정상적으로 종료되거나 예외가 발생하여 종료된 경우 이 상태로 들어간다.
        - 스레드는 한 번 종료되면 다시 시작할 수 없다.

    자바 스레드의 상태 전이 과정
        1. New      → Runnable: start() 메서드를 호출하면 스레드가 Runnable 상태로 전이된다.
        2. Runnable → Blocked/Waiting/Timed Waiting: 스레드가 락을 얻지 못하거나 wait() 또는 sleep() 메서드를 호출할 때 해당 상태로 전이된다.
        3. Blocked/Waiting/Timed Waiting → Runnable: 스레드가 락을 얻거나 기다림이 완료되면 다시 Runnable 상태로 돌아간다.
        4. Runnable → Terminated: 스레드의 run() 메서드가 완료되면 스레드는 Terminated 상태가 된다.
    */

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyRunnable(), "myThread");
        log("myThread.state1 : " + thread.getState());
        log("myThread.start()");
        thread.start();
        //main 스레드가 myThread 의 TIMED_WAITING 상태를 확인하기 위해 1초간 대기
        Thread.sleep(1000);
        log("myThread.state3 : " + thread.getState());
        Thread.sleep(4000);
        log("myThread.state5 : " + thread.getState());
        log("end");
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                log("start");
                log("myThread.state2 : " + Thread.currentThread().getState());
                log("sleep() start");
                Thread.sleep(3000);
                log("sleep() end");
                log("myThread.state4 : " + Thread.currentThread().getState());
                log("end");
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
