import {ComponentRegistry, FormComponent, DivComponent} from "./api.js";
import {CampaignServer} from "./stomp.js";

class DiceComponent extends DivComponent {

    constructor() {
        super();
    }

    render() {
        return this.childs;
    }
}

const campaignServer = new CampaignServer();
campaignServer.addMaintainedId("do-roll", "dice-sides");
campaignServer.onReceive(message => {
    const greetings = $("#greetings");
    greetings.append("<dice-component>" + message.result + "</dice-component>");
    greetings.scrollTop = greetings.scrollHeight
});

campaignServer.connect("Test");

class DiceRollingComponent extends FormComponent {

    constructor() {
        super();
    }

    onSubmit(event) {
        event.preventDefault();
        console.log("Sending!");
        const dice = {
            sides: $("#dice-sides").val()
        };
        console.log(dice);
        campaignServer.rollDice([dice]);
        return false
    }

    render() {
        return (
            '  <div class="form-group">' +
            '    <input class="form-control" type="number" id="dice-sides" required placeholder="Würfel-Seiten"/>' +
            '  </div>' +
            '  <div class="form-group">' +
            '     <input class="form-control btn btn-primary" type="submit" value="Würfeln" id="do-roll"/>' +
            '  </div>'
        )
    }
}

const registry = new ComponentRegistry();
registry.register("dice-component", DiceComponent);
registry.register("dice-form", DiceRollingComponent);