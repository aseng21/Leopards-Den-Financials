import{inputFieldProperties as o,labelProperties as s,helperTextProperties as p,errorMessageProperties as l}from"./vaadin-text-field-0b3db014-pmR_1hbZ.js";import{b_ as r,bZ as e,bY as i,c8 as m,c9 as c}from"./indexhtml-EwR-znj8.js";const v={tagName:"vaadin-time-picker",displayName:"Time Picker",elements:[{selector:"vaadin-time-picker::part(input-field)",displayName:"Input field",properties:o},{selector:"vaadin-time-picker::part(toggle-button)",displayName:"Toggle button",properties:[r.iconColor,r.iconSize]},{selector:"vaadin-time-picker::part(label)",displayName:"Label",properties:s},{selector:"vaadin-time-picker::part(helper-text)",displayName:"Helper text",properties:p},{selector:"vaadin-time-picker::part(error-message)",displayName:"Error message",properties:l},{selector:"vaadin-time-picker-overlay::part(overlay)",displayName:"Overlay",properties:[e.backgroundColor,e.borderColor,e.borderWidth,e.borderRadius,e.padding]},{selector:"vaadin-time-picker-overlay vaadin-time-picker-item",displayName:"Overlay items",properties:[i.textColor,i.fontSize,i.fontWeight]},{selector:"vaadin-time-picker-overlay vaadin-time-picker-item::part(checkmark)::before",displayName:"Overlay item checkmark",properties:[r.iconColor,r.iconSize]}],async setupElement(a){a.overlayClass=a.getAttribute("class"),a.value="00:00",await new Promise(t=>setTimeout(t,10))},openOverlay:m,hideOverlay:c};export{v as default};
