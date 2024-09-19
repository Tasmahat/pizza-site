import React, {useEffect, useState} from "react";
import ReactDOM from "react-dom/client";
import {wait} from "@testing-library/user-event/dist/utils";

function CreatePizza() {
    const [loading, setLoading] = useState(false);
    const [ingredients, setIngredients] = useState([]);
    let fetchAdr = 'api/ingredients';
    let loadingImage = false;
    let imageId, file, name, cost;
    let ingredientNum = 0;

    useEffect(() => {
        setLoading(true);

        fetch(fetchAdr)
            .then(response => response.json())
            .then(data => {
                setIngredients(data);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    };

    let ingredOptions= ingredients.map(ingredient => {
        return(
            <option key={ingredient.id} value={ingredient.id}>{ingredient.name}</option>
        )
    });

    const addIngredient = () => {
        if(ingredientNum<10) {
            ingredientNum++;
            let select = document.createElement('select');
            select.id = "ingredient" + ingredientNum;
            select.className = "ingredient-choice";

            ingredients.map(ingredient => {
                let option = document.createElement('option');
                option.key = ingredient.id;
                option.value = ingredient.id;
                option.id = ingredient.id;
                option.innerHTML = ingredient.name;
                select.appendChild(option);
            });

            document.getElementById("create-pizza").appendChild(
                select
            );
        } else {
            alert("Столько ингредиентов на пиццу не поместиться!");
        }
    };

    const deleteIngredient = () => {
        if(ingredientNum>0) {
            document.getElementById("ingredient" + ingredientNum).remove();
            ingredientNum--;
        } else {
            alert("В пицце должен быть хотя бы 1 ингредиент!!!");
        }
    }

    function fileListener(e) {
        file = e.target.files[0];
    }

    function PostRequest() {
        name = document.getElementById("name").value;
        cost = document.getElementById("cost").value;

        if(name === "") {
            alert("Напишите название пиццы!");
            return;
        }

        if(cost === "") {
            alert("Напишите цену пиццы!");
            return;
        } else if(isNaN(+cost)) {
            alert("Цена пишется цифрами!");
            return;
        }

        if (file === undefined) {
            alert("У пиццы обязательно должна быть картинка!");
            return;
        }

        loadingImage = true;
        console.log(loadingImage+"B")
        const data = new FormData();

        data.append("file", file);

        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
                console.log(this.responseText);
                imageId = this.response.substring(6).split(',',1).toString();
                loadingImage = false;
            }
        });

        xhr.open("POST", "api/images?=");

        xhr.send(data);

        uploadPizza();
    }

    async function uploadPizza() {
        while(loadingImage) {
            console.log("pop")
            await new Promise(r => setTimeout(r, 500));
        }
        let ingredient = [];

        while(ingredientNum>=0){
            ingredient.push(document.getElementById("ingredient"+ingredientNum).value);
            ingredientNum--;
            console.log(ingredient)
        }

        const data = new FormData();
        data.append("name", name);
        data.append("cost", cost);
        data.append("ingredient_id", ingredient);
        data.append("image_id", imageId)

        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
                console.log(this.response);
                if(!this.response.includes("error")) {
                    alert("Пицца была создана!");
                } else {
                    alert("Что-то пошло не так!\nПицца не была создана")
                }
            }
        });

        xhr.open("POST", "api/api/upload?=");

        xhr.send(data);
    }

    return(
        <body>
            <h1 className={"create-pizza-header"}>Создайте свою пиццу!</h1>
            <div className={"create-pizza-form"}>
                <div>
                    <button type={"button"} onClick={addIngredient} className={"change-ingredient-button"}>
                        Добавить ингредиент
                    </button>
                    <button type={"button"} onClick={deleteIngredient} className={"change-ingredient-button"}>
                        Удалить ингредиент
                    </button>
                </div>
                <input type="text" id="name" placeholder={"Название пиццы"} className={"input-box"}/>
                <input type="text" id="cost" placeholder={"Стоимость пиццы"} className={"input-box"}/>
                <form id={"create-pizza"} className={"ingredient-choice-box"}>
                    <select id={"ingredient0"} className={"ingredient-choice"}>
                        {ingredOptions}
                    </select>
                </form>
                <input type={"file"} id={"file"} onChange={fileListener} className={"add-file"}></input>
                <button type='button' onClick={PostRequest} className={"create-form-button"}>Добавить пиццу</button>
            </div>
        </body>
    );
}
export default CreatePizza;