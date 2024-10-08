package thread.cas;

public class ACompareAndSwap {
    /*
    락 기반 방식의 문제점
        SyncInteger 와 같은 클래스는 데이터를 보호하기 위해 락을 사용한다.
        여기서 말하는 락은 synchronized, Lock(ReentrantLock) 등을 사용하는 것을 말한다.
        락은 특정 자원을 보호하기 위해 스레드가 해당 자원에 대한 접근하는 것을 제한한다.
        락이 걸려 있는 동안 다른 스레드들은 해당 자원에 접근할 수 없고 락이 해제될 때까지 대기해야 한다.
        또한 락 기반 접근에서는 락을 획득하고 해제하는 데 시간이 소요된다.

        예를 들어서 락을 사용하는 연산이 있다고 가정하자. 락을 사용하는 방식은 다음과 같이 작동한다.
            1. 락이 있는지 확인한다.
            2. 락을 획득하고 임계 영역에 들어간다.
            3. 작업을 수행한다.
            4. 락을 반납한다.
        여기서 락을 획득하고 반납하는 과정이 계속 반복된다.
        10000번의 연산이 있다면 10000번의 연산 모두 같은 과정을 반복한다.
        이렇듯 락을 사용하는 방식은 직관적이지만 상대적으로 무거운 방식이다.

    CAS
        이런 문제를 해결하기 위해 락을 걸지 않고 원자적인 연산을 수행할 수 있는 방법이 있는데 이것을 CAS(Compare-And-Swap, Compare-And-Set) 연산이라 한다.
        이 방법은 락을 사용하지 않기 때문에 락 프리(lock-free) 기법이라 한다.
        참고로 CAS 연산은 락을 완전히 대체하는 것은 아니고 작은 단위의 일부 영역에 적용할 수 있다.
        기본은 락을 사용하고 특별한 경우에 CAS 를 적용할 수 있다고 생각하면 된다.

    두 스레드가 동시에 실행되면서 문제가 발생하는 상황을 스레드가 충돌했다고 표현한다.
    이 과정에서 충돌이 발생할 때마다 반복해서 다시 시도하므로 결과적으로 락 없이 데이터를 안전하게 변경할 수 있다.
    CAS 를 사용하는 방식은 충돌이 드물게 발생하는 환경에서는 락을 사용하지 않으므로 높은 성능을 발휘할 수 있다.
    이는 락을 사용하는 방식과 비교했을 때 스레드가 락을 획득하기 위해 대기하지 않기 때문에 대기 시간과 오버헤드가 줄어드는 장점이 있다.

    그러나 충돌이 빈번하게 발생하는 환경에서는 성능에 문제가 될 수 있다.
    여러 스레드가 자주 동시에 동일한 변수의 값을 변경하려고 시도할 때 CAS 는 자주 실패하고 재시도해야 하므로 성능 저하가 발생할 수 있다.
    이런 상황에서는 반복문을 계속 돌기 때문에 CPU 자원을 많이 소모하게 된다.

    락(Lock) 방식
        - 비관적(pessimistic) 접근법
        - 데이터에 접근하기 전에 항상 락을 획득
        - 다른 스레드의 접근을 막음
        - "다른 스레드가 방해할 것이다"라고 가정

        동기화 락의 장점
        - 충돌 관리
            락을 사용하면 하나의 스레드만 리소스에 접근할 수 있으므로 충돌이 발생하지 않는다.
            여러 스레드가 경쟁할 경우에도 안정적으로 동작한다.
        - 안정성
            복잡한 상황에서도 락은 일관성 있는 동작을 보장한다.
        - 스레드 대기
            락을 대기하는 스레드는 CPU 를 거의 사용하지 않는다.

        동기화 락의 단점
            - 락 획득 대기 시간
                스레드가 락을 획득하기 위해 대기해야 하므로, 대기 시간이 길어질 수 있다.
            - 컨텍스트 스위칭 오버헤드
                락을 사용하면 락 획득을 대기하는 시점과 또 락을 획득하는 시점에 스레드의 상태가 변경된다.
                이때 컨텍스트 스위칭이 발생할 수 있으며 이로 인해 오버헤드가 증가할 수 있다.

    CAS(Compare-And-Swap) 방식
        - 낙관적(optimistic) 접근법
        - 락을 사용하지 않고 데이터에 바로 접근
        - 충돌이 발생하면 그때 재시도
        - "대부분의 경우 충돌이 없을 것이다"라고 가정

        CAS 의 장점
            - 낙관적 동기화
                락을 걸지 않고도 값을 안전하게 업데이트할 수 있다.
                CAS 는 충돌이 자주 발생하지 않을 것이라고 가정한다.
                이는 충돌이 적은 환경에서 높은 성능을 발휘한다.
            - 락 프리(Lock-Free)
                CAS 는 락을 사용하지 않기 때문에 락을 획득하기 위해 대기하는 시간이 없다.
                따라서 스레드가 블로킹되지 않으며 병렬 처리가 더 효율적일 수 있다.

        CAS 의 단점
            - 충돌이 빈번한 경우
                여러 스레드가 동시에 동일한 변수에 접근하여 업데이트를 시도할 때 충돌이 발생할 수 있다.
                충돌이 발생하면 CAS 는 루프를 돌며 재시도해야 하며 이에 따라 CPU 자원을 계속 소모할 수 있다.
                반복적인 재시도로 인해 오버헤드가 발생할 수 있다.
            - 스핀락과 유사한 오버헤드
                CAS 는 충돌 시 반복적인 재시도를 하므로 이 과정이 계속 반복되면 스핀락과 유사한 성능 저하가 발생할 수 있다.
                특히 충돌 빈도가 높을수록 이런 현상이 두드러진다.


    결론
        일반적으로 동기화 락을 사용하고 아주 특별한 경우에 한정해서 CAS 를 사용해서 최적화해야 한다.
        CAS 를 통한 최적화가 더 나은 경우는 스레드가 RUNNABLE -> BLOCKED, WAITING 상태에서 다시 RUNNABLE 상태로 가는 것 보다
        스레드를 RUNNABLE 로 살려둔 상태에서 계속 락 획득을 반복 체크하는 것이 더 효율적인 경우에 사용해야 한다.
        하지만 이 경우 대기하는 스레드가 CPU 자원을 계속 소모하기 때문에 대기 시간이 아주 짧아야 한다.
        따라서 임계 영역이 필요는 하지만 연산이 길지 않고 매우 짧게 끝날 때 사용해야 한다.
        예를 들어 숫자 값의 증가, 자료 구조의 데이터 추가, 삭제와 같이 CPU 사이클이 금방 끝나지만 안전한 임계 영역 또는 원자적인 연산이 필요한 경우에 사용해야 한다.
        반면에 데이터베이스를 기다린다거나 다른 서버의 요청을 기다리는 것 처럼 오래 기다리는 작업에 CAS 를 사용하면 CPU 를 계속 사용하며 기다리는 최악의 결과가 나올 수도 있다.
        이런 경우에는 동기화 락을 사용해야 한다.
        또한 CAS 는 충돌 가능성이 낮은 환경에서 매우 효율적이지만 충돌 가능성이 높은 환경에서는 성능 저하가 발생할 수 있다.
        이런 경우에는 상황에 맞는 적절한 동기화 전략을 사용하는 것이 중요하다.
        때로는 락이 더 나은 성능을 발휘할 수 있으며 CAS 가 항상 더 빠르다고 단정할 수는 없다.
        따라서 각 접근 방식의 특성을 이해하고 애플리케이션의 특정 요구사항과 환경에 맞는 방식을 선택하는 것이 필요하다.

    우리가 사용하는 많은 자바 동시성 라이브러리들, 동기화 컬렉션들은 성능 최적화를 위해 CAS 연산을 적극 활용한다.
    덕분에 실무에서 직접 CAS 연산을 사용하는 사용하는 일은 매우 드물다.
    대신에 CAS 연산을 사용해서 최적화 되어 있는 라이브러리들을 이해하고 편리하게 사용할 줄 알면 충분하다.
    */
}
