import{f as d,Y as g,X as b,I as n,n as l,z as y,Z as x,h as w,A as h,E as k,j as C,k as I,l as P,m as $,o as O,M as E,g as m,G as L,P as D,N as T,p as A,r as K,H as M}from"./indexhtml-EwR-znj8.js";import{o as U}from"./base-panel-__PMv33m-iiDpJ75l.js";const j="copilot-outline-panel{padding:0;position:relative}copilot-outline-panel vaadin-grid::part(cell){padding:var(--space-100) var(--space-150);cursor:default}copilot-outline-panel vaadin-grid::part(selected-row){background:var(--blue-100);color:var(--color-high-contrast)}copilot-outline-panel vaadin-grid::part(cell):focus-visible,copilot-outline-panel vaadin-grid::part(row):focus-visible{outline:2px solid var(--blue-300);outline-offset:-2px}copilot-outline-panel vaadin-grid-tree-toggle::part(toggle){color:var(--border-color-high-contrast);opacity:0}copilot-outline-panel:hover vaadin-grid-tree-toggle::part(toggle){opacity:1}";var S=Object.defineProperty,H=Object.getOwnPropertyDescriptor,v=(e,t,i,o)=>{for(var a=o>1?void 0:o?H(t,i):t,r=e.length-1,s;r>=0;r--)(s=e[r])&&(a=(o?s(t,i,a):s(a))||a);return o&&a&&S(t,i,a),a};let c=class extends L{constructor(){super(),this.removers=[],this.text=""}static get styles(){return D`
      :host {
        display: block;
        position: absolute;
        inset: 0;
        z-index: 100000;
        background: rgba(255, 255, 255, 0.6);
      }

      .text {
        padding: 1em;
        position: absolute;
        top: 50%;
        text-align: center;
        width: 100%;
        background: white;
      }
    `}render(){return l`
      <div class="text">
        <a href="javascript:void(0);" @click="${this.signUpOrLogin}">Sign up</a> or
        <a href="javascript:void(0);" @click="${this.signUpOrLogin}">Log in</a> to ${this.text}.
      </div>
    `}connectedCallback(){super.connectedCallback();const e=t=>{t.data==="validate-license"&&this.licenseValidated()};window.addEventListener("message",e),this.removers.push(()=>window.removeEventListener("message",e)),["focus","click","keydown","keyup","keypress"].forEach(t=>{const i=o=>{const a=M(this);a&&(this.contains(o.target)||o.composed&&o.composedPath().includes(this)||(a.contains(o.target)||o.composed&&o.composedPath().includes(a))&&(o.preventDefault(),o.stopPropagation(),t==="focus"&&o.target.blur()))};document.body.addEventListener(t,i,{capture:!0}),this.removers.push(()=>document.body.removeEventListener(t,i,{capture:!0}))})}async licenseValidated(){await T()}disconnectedCallback(){super.disconnectedCallback(),this.removers.forEach(e=>e())}async signUpOrLogin(){await A(`${K}log-in`,{},e=>{const{loginUrl:t}=e.data;t&&window.open(t)})}};v([E({type:String})],c.prototype,"text",2);c=v([m("copilot-login-to-access")],c);var z=Object.defineProperty,F=Object.getOwnPropertyDescriptor,f=(e,t,i,o)=>{for(var a=o>1?void 0:o?F(t,i):t,r=e.length-1,s;r>=0;r--)(s=e[r])&&(a=(o?s(t,i,a):s(a))||a);return o&&a&&z(t,i,a),a};function u(e){if(e.currentTarget)return e.currentTarget.getEventContext(e).item}let p=class extends U{constructor(){super(...arguments),this.expandedItems=[],this.initialExpandDone=!1,this.filter=e=>d(e)?!0:!!g(e),this.getFilteredChildren=e=>{const t=b(e);if(t.length===0)return[];const i=t.filter(this.filter);return i.length===t.length?t:t.flatMap(o=>i.includes(o)?o:this.getFilteredChildren(o))},this.dataProvider=(e,t)=>{if(!this.reactApp)t([],0);else if(!e.parentItem)t([this.reactApp],1);else{const i=this.getFilteredChildren(e.parentItem);t(i,i.length)}}}connectedCallback(){super.connectedCallback(),this.componentTreeUpdated(),this.onCommand("component-tree-updated",()=>this.componentTreeUpdated())}render(){return n.userInfo?l`
      ${n.userInfo.proKey?"":l`<copilot-login-to-access .text=${"use the outline"}></copilot-login-to-access>`}
      <style>
        ${j}
      </style>
      <vaadin-grid
        all-rows-visible
        .dataProvider=${this.dataProvider}
        .selectedItems=${n.getSelections.map(e=>y(e.element))}
        @keydown=${this.gridKeyDown}
        @mousemove=${this.gridItemMouseMove}
        @click=${this.gridItemClick}>
        <vaadin-grid-tree-column
          auto-width
          .__getToggleContent=${this.renderToggleColumn}
          .__isLeafItem=${this.isLeafItem.bind(this)}></vaadin-grid-tree-column>
      </vaadin-grid>
    `:l`Initializing...`}renderToggleColumn(e,t){let i="";return g(t)?i="♦ ":x(t)&&(i="</> "),`${i}${w(t)}`}isLeafItem(e){return this.getFilteredChildren(e).length===0}gridKeyDown(e){e.code==="Space"&&!e.altKey&&!e.metaKey&&!e.ctrlKey&&!e.shiftKey&&(e.preventDefault(),e.stopPropagation())}gridItemMouseMove(e){let t;const i=u(e);i&&d(i)&&(t=h(i)),t?n.setHighlighted({element:t}):n.setHighlighted(void 0),e.preventDefault(),e.stopPropagation()}gridItemClick(e){const t=u(e);if(!t||!d(t))return;!e.metaKey&&!e.ctrlKey&&n.clearSelection();const i=h(t);i?n.isSelected(i)?n.deselect(i):n.select(i):console.error("Unable to find element for selection",t),k("use-outline")}updated(e){super.updated(e),this.initialExpandDone||this.reactApp&&this.grid&&(this.grid.expandedItems=[this.reactApp,...C(this.reactApp)],this.initialExpandDone=!0)}componentTreeUpdated(){this.reactApp=I(),this.grid&&(this.reactApp&&(this.grid.expandedItems=this.grid.expandedItems.map(e=>P(e))),this.grid.clearCache()),this.requestUpdate()}};f([$("vaadin-grid")],p.prototype,"grid",2);p=f([m("copilot-outline-panel")],p);const V={header:"Outline",expanded:!0,draggable:!0,panelOrder:0,panel:"left",floating:!1,tag:"copilot-outline-panel",showOn:[O.HillaReact]},_={init(e){e.addPanel(V)}};window.Vaadin.copilotPlugins.push(_);export{p as CopilotOutlinePanel};
