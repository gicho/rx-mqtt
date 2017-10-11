package rx.mqtt.android;

import android.util.Pair;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ServiceController;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
//@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RxMqttTest {
    public static final String url = "tcp://test.mosquitto.org:1883";
    public static final String topic = "#";

    //@Test
    // MqttAndroidClient cannot retrieve MqttService by intent
    public void testMsg() throws MqttException {
        final MqttAndroidClient client = RxMqtt.client(RuntimeEnvironment.application, url);
        TestObserver observer = RxMqtt.message(client, topic).doOnNext(new Consumer<Pair<MqttMessage, String>>() {
            @Override
            public void accept(Pair<MqttMessage, String> msg) throws Exception {
                System.out.println(msg.toString());
            }
        }).test();
        try {
            observer.await(10, SECONDS);
        } catch (InterruptedException e) {
            fail(e.toString());
            e.printStackTrace();
        }
        observer.assertNoErrors();
        //assertTrue(true);
    }

    @Test
    public void testConnect() throws MqttException {
        /*
        final MqttAndroidClient client = RxMqtt.client(RuntimeEnvironment.application, url);
        TestObserver observer = RxMqtt.connect(client).doOnNext(new Consumer<IMqttToken>() {
            @Override
            public void accept(IMqttToken token) throws Exception {
                System.out.println(token);
            }
        }).test();
        observer.awaitTerminalEvent();
        observer.assertTerminated();
        */
    }

    //private MqttService mqttService;
    //private ServiceController<MqttService> controller;

    @Before
    public void setUp() {
        //controller = Robolectric.buildService(MqttService.class);
        //mqttService = controller.attach().create().get();
    }

    @After
    public void tearDown() {
        //controller.destroy();
    }
}
