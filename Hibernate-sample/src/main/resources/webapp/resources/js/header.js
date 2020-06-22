function docReady(fn) {
    // see if DOM is already available
    if (document.readyState === "complete" || document.readyState === "interactive") {
        // call on next available tick
        setTimeout(fn, 1);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}
const loadComponent = async element => {
    const url = element.getAttribute('data-include');
    const response = await fetch(`${url}`)
    const html = await response.text();
    element.innerHTML = html;


}

window.onload = function() {

    let elements = document.getElementsByTagName('*');
    //elements = Array.from(elements).filter(element => element.hasAttribute && element.hasAttribute('data-include'));
    elements = [...elements].filter(element => element.hasAttribute && element.hasAttribute('data-include'));
    [...elements].forEach(loadComponent);


}