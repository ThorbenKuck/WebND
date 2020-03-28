export function autoBind(type) {
    console.log(type);
    console.log(Object.getOwnPropertyNames(type)
        .filter(x => x !== 'constructor'))
}

function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

export class Component extends HTMLElement {

    get childs() {
        return this.innerHTML
    }

    constructor() {
        // always call super() first
        super();
    }

    connectedCallback() {
        this.onMount();
        this.setNode(this.innerRender())
    }

    setNode(node) {
        this.innerHTML = "";
        if(node !== null) {
            this.appendChild(node)
        }
    }

    innerRender() {
        return null
    }

    disconnectedCallback() {
        this.onOnmount();
    }

    attributeChangedCallback(name, oldVal, newVal) {
        // Do nothing
    }

    adoptedCallback() {
        // Do nothing
    }

    onMount() {}

    onOnmount() {}

    render() {}
}

export class FormComponent extends Component {

    constructor() {
        // always call super() first
        super();
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(ev) {
        return false
    }

    innerRender() {
        this.onMount();
        const form = document.createElement("form");

        form.innerHTML = this.render();
        form.onsubmit = this.onSubmit;

        return form
    }
}

export class DivComponent extends Component {

    constructor() {
        // always call super() first
        super();
    }

    innerRender() {
        const div = document.createElement("div");
        div.innerHTML = this.render();
        return div
    }
}

export class ComponentRegistry {
    register(name, constructor) {
        customElements.define(name, constructor);
    }

    subtype(type, name, constructor) {
        customElements.define(name, constructor, {extends: type});
    }
}