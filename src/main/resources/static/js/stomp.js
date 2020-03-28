export class CampaignServer {

    stompClient = null;
    resultCallback = null;
    connectCallback = null;
    disconnectCallback = null;
    campaignName = null;
    maintainingIds = [];

    constructor() {
        this.setConnected = this.setConnected.bind(this);
        this.connect = this.connect.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleError = this.handleError.bind(this);
        this.disconnect = this.disconnect.bind(this);
        this.send = this.send.bind(this);
        this.receivedData = this.receivedData.bind(this);
        this.onConnect = this.onConnect.bind(this);
        this.onDisconnect = this.onDisconnect.bind(this);
        this.onReceive = this.onReceive.bind(this);
        this.isConnected = this.isConnected.bind(this);
        this.dispatch = this.dispatch.bind(this);
        this.addMaintainedId = this.addMaintainedId.bind(this);
        this.handleConnected = this.handleConnected.bind(this);
    }

    isConnected() {
        return this.stompClient !== null
    }

    connect(campaignName) {
        if (this.isConnected()) {
            this.disconnect()
        }
        const socket = new SockJS('gs-guide-websocket');
        this.campaignName = campaignName;
        let stompClient = Stomp.over(socket);

        const callback = () => {
            stompClient.subscribe('/topics/campaigns/' + campaignName + "/roll", (result) => {
                console.log("received data");
                this.receivedData(JSON.parse(result.body));
            });
            this.setConnected(true);
        };

        stompClient.connect({}, {}, callback, this.handleError, this.handleClose);

        this.stompClient = stompClient
    }

    disconnect() {
        if (this.isConnected()) {
            this.stompClient.disconnect();
        }
        this.setConnected(false);
    }

    setConnected(connected) {
        if (connected) {
            this.handleConnected()
        } else {
            this.handleClose()
        }
    }

    handleConnected() {
        for (let id in this.maintainingIds) {
            const element = $("#" + id);
            element.prop("disabled", false);
            element.show();
        }
        if (this.connectCallback != null) {
            this.connectCallback()
        } else {
            console.debug("No connect callback set")
        }
    }

    handleClose() {
        for (let id in this.maintainingIds) {
            const element = $("#" + id);
            element.prop("disabled", false);
            element.hide();
        }
        if (this.disconnectCallback) {
            this.disconnectCallback()
        } else if (this.campaignName) {
            console.warn("Connection to " + this.campaignName + " lost")
        } else {
            console.debug("No disconnect callback set")
        }
    }

    handleError(error) {
        console.warn(error)
    }

    dispatch(path, content) {
        if (!this.isConnected()) {
            throw Error("Not connected!")
        }
        this.stompClient.send(path, {}, JSON.stringify(content));
    }

    send(content) {
        this.dispatch("/in/campaigns/" + this.campaignName + "/test", content);
    }

    rollDice(dice) {
        this.dispatch("/in/campaigns/" + this.campaignName + "/roll", dice);
    }

    receivedData(message) {
        if (this.resultCallback) {
            this.resultCallback(message)
        } else {
            console.warn("Unhandled message", message)
        }
    }

    onConnect(callback) {
        this.connectCallback = callback
    }

    onDisconnect(callback) {
        this.disconnectCallback = callback
    }

    onReceive(callback) {
        this.resultCallback = callback
    }

    addMaintainedId() {
        const argumentsArray = Array.prototype.slice.apply(arguments);
        for(let argument in argumentsArray) {
            this.maintainingIds.push(argument)
        }
    }
}