package ru.example.alarmmonitoring.integration;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.PubNubException;
import com.pubnub.api.UserId;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import ru.example.alarmmonitoring.fragments.viewmodel.SettingViewModel;

public class PubNubClient implements DataClient {
    private final String userId;
    private final List<String> channelName = Collections.singletonList("myChannel");
    private PubNub pubnub = null;
    private MutableLiveData<String> serverStatusBackground = null;

    private SettingViewModel settingViewModel = null;
    private final MessageController messageController;

    public PubNubClient(String activeEngineer, MessageController messageController) {
        this.userId = activeEngineer;
        this.messageController = messageController;
        this.messageController.setActiveEngineer(activeEngineer);
    }

    private void register(String userId, MutableLiveData<Boolean> isSuccessfulConnection) {
        PNConfiguration pnConfiguration;
        try {
            pnConfiguration = new PNConfiguration(new UserId(userId));
        } catch (PubNubException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        pnConfiguration.setSubscribeKey("sub-c-69573f44-e8f5-402b-9ebd-786a5cb255a2");
        pnConfiguration.setPublishKey("pub-c-56b4868c-19e2-4c60-bbd2-ef1c52eecb25");

        pubnub = new PubNub(pnConfiguration);

        // create message payload using Gson
        final JsonObject messageJsonObject = new JsonObject();
        JsonElement jsonElement = new JsonPrimitive("Hello World");
        messageJsonObject.addProperty("msg", jsonElement.getAsString());

        pubnub.addListener(new SubscribeCallback.BaseSubscribeCallback() {
            @Override
            public void status(@NotNull PubNub pubnub, @NotNull PNStatus status) {
                if (settingViewModel.validSwitchTransaction()) {
                    // Успешное установление соединения с сервером
                    if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                        serverStatusBackground.postValue("ok_circle");
                        isSuccessfulConnection.postValue(true);

                        // Успешное переподключение к серверу после потери связи
                    } else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                        serverStatusBackground.postValue("ok_circle");
                        isSuccessfulConnection.postValue(true);
                    } else {
                        serverStatusBackground.postValue("loss_of_contact_circle");
                        isSuccessfulConnection.postValue(false);

                        // Неизвестная категория статуса. Это может возникнуть, если категория статуса не соответствует ни одной из предопределенных категорий.
                        if (status.getCategory() == PNStatusCategory.PNUnknownCategory) {

                            //Успешное получение подтверждения от сервера о выполнении операции.
                        } else if (status.getCategory() == PNStatusCategory.PNAcknowledgmentCategory) {

                            // Доступ к определенным ресурсам или операциям был отклонен из-за недостаточных прав.
                        } else if (status.getCategory() == PNStatusCategory.PNAccessDeniedCategory) {

                            // Истечение времени ожидания при выполнении операции.
                        } else if (status.getCategory() == PNStatusCategory.PNTimeoutCategory) {

                            // Проблемы с сетью
                        } else if (status.getCategory() == PNStatusCategory.PNNetworkIssuesCategory) {

                            // Потеря соединения с сервером.
                        } else if (status.getCategory() == PNStatusCategory.PNDisconnectedCategory) {

                            // Неожиданный разрыв соединения.
                        } else if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {

                            // Отмена операции.
                        } else if (status.getCategory() == PNStatusCategory.PNCancelledCategory) {

                            // Некорректный запрос.
                        } else if (status.getCategory() == PNStatusCategory.PNBadRequestCategory) {

                            // Превышение допустимой длины URI.
                        } else if (status.getCategory() == PNStatusCategory.PNURITooLongCategory) {

                            // Ошибка в выражении фильтра.
                        } else if (status.getCategory() == PNStatusCategory.PNMalformedFilterExpressionCategory) {

                            // Некорректный формат (Разбор JSON) ответа от сервера.
                        } else if (status.getCategory() == PNStatusCategory.PNMalformedResponseCategory) {

                            // Ошибка расшифровки сообщения.
                        } else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                            // Ошибка при попытке установить TLS соединение с сервером.
                        } else if (status.getCategory() == PNStatusCategory.PNTLSConnectionFailedCategory) {

                            // Ошибка, недоверенный сертификат TLS.
                        } else if (status.getCategory() == PNStatusCategory.PNTLSUntrustedCertificateCategory) {

                            // Количество запросов или сообщений превысило установленный лимит.
                        } else if (status.getCategory() == PNStatusCategory.PNRequestMessageCountExceededCategory) {

                            // Попытки переподключения к серверу были исчерпаны, и не удалось восстановить соединение.
                        } else if (status.getCategory() == PNStatusCategory.PNReconnectionAttemptsExhaustedCategory) {

                            // Превышение лимита скорости для запросов к серверу.
                        } else if (status.getCategory() == PNStatusCategory.PNRateLimitExceededCategory) {

                        }
                    }
                }
            }

            @Override
            public void message(@NotNull PubNub pubnub, @NotNull PNMessageResult message) {
                if (settingViewModel.validSwitchTransaction()) {
                    JsonObject json = message.getMessage().getAsJsonObject();
                    String msg = json.get("msg").getAsString();
                    messageController.toAccept(msg);
                    settingViewModel.determineEmployment();
                }
            }
        });
    }

    @Override
    public void startPolling(MutableLiveData<String> buttonBackground, SettingViewModel settingViewModel) {
        this.serverStatusBackground = buttonBackground;
        this.settingViewModel = settingViewModel;
        if (settingViewModel.validSwitchTransaction()) {
            buttonBackground.postValue("loading_circle");
            register(this.userId, settingViewModel.isSuccessfulConnection);
            subscribe();

            messageController.toAccept("1 1 0 T000 10:05 70"); //для теста
            messageController.toAccept("1 1 1 E111 10:05 70"); //для теста
            messageController.toAccept("1 1 2 E222 10:05 70"); //для теста
        }
    }

    private void subscribe() {
        pubnub.subscribe()
                .channels(channelName)
                .execute();
    }

    @Override
    public void stopPolling() {
        if (pubnub == null) {
            return;
        }
        pubnub.unsubscribe()
                .channels(channelName)
                .execute();
        pubnub.disconnect();
        pubnub.destroy();
    }
}