import{n as d,c as u,d as g,g as f}from"./indexhtml-EwR-znj8.js";import{o as p}from"./base-panel-__PMv33m-iiDpJ75l.js";var h=Object.defineProperty,b=Object.getOwnPropertyDescriptor,c=(e,t,a,s)=>{for(var r=s>1?void 0:s?b(t,a):t,l=e.length-1,o;l>=0;l--)(o=e[l])&&(r=(s?o(t,a,r):o(r))||r);return s&&r&&h(t,a,r),r};const n=window.Vaadin.devTools;let i=class extends p{constructor(){super(),this.handleFeatureFlags=e=>{this.features=e.data.features},this.features=[]}connectedCallback(){super.connectedCallback(),this.onCommand("featureFlags",this.handleFeatureFlags)}render(){return d` <div class="features-tray">
      ${this.features.map(e=>d` <div class="feature">
            <label class="switch">
              <input
                class="feature-toggle"
                id="feature-toggle-${e.id}"
                type="checkbox"
                ?checked=${e.enabled}
                @change=${t=>this.toggleFeatureFlag(t,e)} />
              <span class="slider"></span>
              ${e.title}
            </label>
            <a class="ahreflike" href="${e.moreInfoLink}" target="_blank">Learn more</a>
          </div>`)}
    </div>`}toggleFeatureFlag(e,t){const a=e.target.checked;n.frontendConnection?(n.frontendConnection.send("setFeature",{featureId:t.id,enabled:a}),n.showNotification(u.INFORMATION,`“${t.title}” ${a?"enabled":"disabled"}`,t.requiresServerRestart?"This feature requires a server restart":void 0,void 0,`feature${t.id}${a?"Enabled":"Disabled"}`)):n.log("error",`Unable to toggle feature ${t.title}: No server connection available`)}};c([g()],i.prototype,"features",2);i=c([f("copilot-features-panel")],i);const v={header:"Features",expanded:!0,draggable:!0,panelOrder:0,panel:"right",floating:!1,tag:"copilot-features-panel"},F={init(e){e.addPanel(v)}};window.Vaadin.copilotPlugins.push(F);export{i as CopilotFeaturesPanel};
