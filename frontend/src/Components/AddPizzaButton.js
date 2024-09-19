import React from "react";

function AddPizzaButton() {
    if(localStorage.getItem('canAddPizza') === "true") {
        return (
            <a href={"/create_form"}>
                <button className={"create-pizza-button"}>
                    Создать пиццу
                </button>
            </a>
        )
    } else {
        return (
            <></>
        )
    }
}

export default AddPizzaButton;