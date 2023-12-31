////////////////////////////////////////////////////////////////////////////////
//
// [SGLEE:2023119SUN_235300] Created
//
////////////////////////////////////////////////////////////////////////////////

package ezen.project.first.team2.app.common.utils.thread;

import java.util.concurrent.atomic.AtomicBoolean;

import ezen.project.first.team2.app.common.utils.SystemUtils;

public class BlueThread {
	public static final int DEFAULT_SLEEP_IN_MILLIS = 10;

	private Thread mThread = null;
	private BlueThreadListener mListener = null;
	private long mSleepInMillis = 0;

	private Object mParam = null;
	private final AtomicBoolean mIsRunning = new AtomicBoolean(false);

	// 생성자
	public BlueThread(BlueThreadListener listener, Object param, int sleepInMillis) {
		this.mListener = listener;
		this.mParam = param;
		this.mSleepInMillis = sleepInMillis;
	}

	// 생성자
	public BlueThread(BlueThreadListener listener, Object param) {
		this(listener, param, DEFAULT_SLEEP_IN_MILLIS);
	}

	// 생성자
	public BlueThread(BlueThreadListener listener) {
		this(listener, null);
	}

	// 스레드 시작
	public void start() throws Exception {
		// 이미 실행 중인 경우 예외 발생
		if (this.isRunning()) {
			String msg = String.format(
					"[BlueThread.start()] " +
							"Thread is already running state!");
			throw new Exception(msg);
		}

		this.mThread = new Thread(() -> {
			// onStart()에서 false를 리턴하면 스레드를 종료한다.
			if (!this.mListener.onStart(this, this.mParam)) {
				this.mListener.onStop(this, this.mParam, false);
				this.mIsRunning.set(false);

				return;
			}

			while (mIsRunning.get()) {
				if (!this.mListener.onRun(this, this.mParam)) {
					this.mIsRunning.set(true);

					this.mListener.onStop(this, this.mParam, false);

					break;
				}

				SystemUtils.sleep(this.mSleepInMillis);
			}

			this.mIsRunning.set(false);
		});

		this.mIsRunning.set(true);
		this.mThread.start();
	}

	// 스레드 종료
	// 스레드 내에서 종료를 하려면 Listener.onRun() 메소드에서 false를 리턴한다.
	public void stop() throws Exception {
		// 실행 중이 아니라면 예외 발생
		if (!isRunning()) {
			String msg = String.format(
					"[BlueThread.stop()] " +
							"Thread is not running state!");
			throw new Exception(msg);
		}

		// 스스로 스레드를 종료하려고 하다면 예외 발생
		if (Thread.currentThread() == mThread) {
			String msg = String.format(
					"[BlueThread.stop()] " +
							"It can't stop its self!");
			throw new Exception(msg);
		}

		this.mIsRunning.set(false);

		this.mThread.join();
		this.mListener.onStop(this, mParam, true);
	}

	// 스레드 실행 여부 확인
	public boolean isRunning() {
		return this.mIsRunning.get();
	}

	// 스레드 종료 대기
	public void join() throws Exception {
		// 스레드가 실행 중이 아니라면 예외 발생
		if (!isRunning()) {
			String msg = String.format(
					"[BlueThread.join()] " +
							"Thread is not running state!");
			throw new Exception(msg);
		}

		// 자기 자신이 wait를 하게 되면 무한 루프에 빠진다!
		if (Thread.currentThread() == this.mThread) {
			String msg = String.format(
					"[BlueThread.join()] " +
							"It can't join(wait) its self!");
			throw new Exception(msg);
		}

		this.mThread.join();
	}
}
