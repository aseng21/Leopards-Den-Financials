import{c as g,n as r,d as h,g as c}from"./indexhtml-EwR-znj8.js";import{o as p}from"./base-panel-__PMv33m-iiDpJ75l.js";var m=Object.defineProperty,u=Object.getOwnPropertyDescriptor,d=(e,t,s,o)=>{for(var i=o>1?void 0:o?u(t,s):t,n=e.length-1,l;n>=0;n--)(l=e[n])&&(i=(o?l(t,s,i):l(i))||i);return o&&i&&m(t,s,i),i};let a=class extends p{constructor(){super(),this.nextMessageId=1,this.transitionDuration=0,this.handleLogEvent=e=>{const t=e.data;this.log(t.type,t.message,t.details,t.link)},this.notifications=[],this.unreadErrors=!1,this.messages=[],this.catchErrors()}connectedCallback(){super.connectedCallback(),this.onCommand("log",this.handleLogEvent),this.transitionDuration=parseInt(window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),10)}activate(){this.unreadErrors=!1,this.updateComplete.then(()=>{const e=this.renderRoot.querySelector(".message-tray .message:last-child");e&&e.scrollIntoView()})}format(e){return e.toString()}catchErrors(){const e=window.Vaadin.ConsoleErrors;window.Vaadin.ConsoleErrors={push:t=>{this.log(g.ERROR,t.map(s=>this.format(s)).join(" ")),e.push(t)}}}render(){return r`<div class="message-tray">${this.messages.map(e=>this.renderMessage(e))}</div>`}renderMessage(e){return r`
      <div
        class="message ${e.type} ${e.deleted?"animate-out":""} ${e.details||e.link?"has-details":""}">
        <div class="message-content">
          <div class="message-heading">${e.message}</div>
          <div class="message-details" ?hidden="${!e.details&&!e.link}">
            ${e.details?r`<p>${e.details}</p>`:""}
            ${e.link?r`<a class="ahreflike" href="${e.link}" target="_blank">Learn more</a>`:""}
          </div>
          ${e.persistentId?r`<div
                class="persist ${e.dontShowAgain?"on":"off"}"
                @click=${()=>this.toggleDontShowAgain(e.id)}>
                Don’t show again
              </div>`:""}
        </div>
      </div>
    `}toggleDontShowAgain(e){const t=this.findNotificationIndex(e);if(t!==-1&&!this.notifications[t].deleted){const s=this.notifications[t];s.dontShowAgain=!s.dontShowAgain,this.requestUpdate()}}findNotificationIndex(e){let t=-1;return this.notifications.some((s,o)=>s.id===e?(t=o,!0):!1),t}log(e,t,s,o){const i=this.nextMessageId;for(this.nextMessageId+=1,this.messages.push({id:i,type:e,message:t,details:s,link:o,dontShowAgain:!1,deleted:!1});this.messages.length>a.MAX_LOG_ROWS;)this.messages.shift();this.requestUpdate(),this.updateComplete.then(()=>{const n=this.renderRoot.querySelector(".message-tray .message:last-child");n?(setTimeout(()=>n.scrollIntoView({behavior:"smooth"}),this.transitionDuration),this.unreadErrors=!1):e===g.ERROR&&(this.unreadErrors=!0)})}};a.MAX_LOG_ROWS=1e3;d([h()],a.prototype,"notifications",2);d([h()],a.prototype,"unreadErrors",2);d([h()],a.prototype,"messages",2);a=d([c("copilot-log-panel")],a);const f={header:"Log",expanded:!0,draggable:!0,panelOrder:0,panel:"bottom",floating:!1,tag:"copilot-log-panel"},v={init(e){e.addPanel(f)}};window.Vaadin.copilotPlugins.push(v);export{a as CopilotLogPanel};
