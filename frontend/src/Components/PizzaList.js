import React, {useEffect, useState} from "react";
import ReactPaginate from "react-paginate";

import Header from "./Header";

import {Mutex} from 'async-mutex';

function PizzaList() {

    const [pizzas, setPizzas] = useState([]);
    const [loading, setLoading] = useState(false);
    const [pageNumber, setPageNumber] = useState(0);
    const [numberOfPages, setNumberOfPages] = useState(0);

    const mutex = new Mutex();

    let fetchAdr = 'http://localhost:8181/api/page/';
    let ingredients = [];

    let imgPath = "./images/";

    const getUser = async () => {
        const response = await fetch(
            "http://localhost:8181/users/get_user",
            {method: "GET", redirect: "follow", credentials: "include"}
        ).then((response) => response);
        const data = await response.json();
        localStorage.setItem('username', data.name);
        localStorage.setItem('canDelPizza', data.canDelPizza);
        localStorage.setItem('canAddPizza', data.canAddPizza)
        console.log(data);
    }

    const getPizzas = async () => {
        setLoading(true);

        const response = await fetch (
            fetchAdr + pageNumber,
            {method: "GET", redirect: "follow", credentials: "include"}
        ).then((response) => response);
        console.log(response)
        const data = await response.json();
        setPizzas(data.content);
        setNumberOfPages(data.totalPages);
        setLoading(false);
    }

    useEffect(() => {
        getUser();
        getPizzas();
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    };

    let pizzaList = [];
    let idImage;

        function deletePizza(event) {
        console.log(event.target.id);
        idImage = event.target.id.substring(3);
        if(parseInt(idImage)){
            sendDeleteRequest();
        } else {
            alert("Что-то пошло не так!\nНевозможно удалить!")
        }
    }

    function sendDeleteRequest() {
        const data = new FormData();
        data.append("id", idImage);

        const xhr = new XMLHttpRequest();
        xhr.withCredentials = true;

        xhr.addEventListener("readystatechange", function () {
            if (this.readyState === this.DONE) {
                console.log(this.responseText);
                if(!this.response.includes("error")) {
                    alert("Пицца была удалена!");
                    window.location.reload();
                } else {
                    alert("Что-то пошло не так!\nПицца не была удалена!");
                }
            }
        });

        xhr.open("DELETE", "api/api/delete?=");

        xhr.send(data);
    }

    pizzas.map (pizza => {
        ingredients = pizza.ingredientsSet.map(ingredient => {
            return(ingredient.name + ", ");
        });

        let temp = ingredients[ingredients.length-1];
        ingredients[ingredients.length-1] = temp.substring(0, temp.length-2);

        const image = require(imgPath + pizza.image.key + '.jpg');

        if(localStorage.getItem('canDelPizza') === "true") {
            pizzaList.push(
                <div className={"pizza-object"} key={pizza.id}>
                    <img className={"pizza-object-img"} src={image}/>
                    <button onClick={deletePizza} className={"delete-pizza-button"} id={"del" + pizza.id}>
                        X
                    </button>
                    <div style={{fontSize: "30px", fontWeight: "bold"}}>{pizza.name}</div>
                    <div style={{fontSize: "20px"}}>Цена: {pizza.cost}</div>
                    <div>Состав: {ingredients}</div>
                </div>
            );
        } else {
            console.log(localStorage.getItem('canDelPizza'))
            pizzaList.push(
                <div className={"pizza-object"} key={pizza.id}>
                    <img className={"pizza-object-img"} src={image}/>
                    <div style={{fontSize: "30px", fontWeight: "bold"}}>{pizza.name}</div>
                    <div style={{fontSize: "20px"}}>Цена: {pizza.cost}</div>
                    <div>Состав: {ingredients}</div>
                </div>
            );
        }

        ingredients = [];

        return 0;
    });

    function changePage(event) {
        setPageNumber(event.selected);
        mutex
            .runExclusive(() => {
            })
            .then(() => {
                fetch(fetchAdr + pageNumber,
                    {method: "GET", redirect: "follow", credentials: "include"})
                    .then(response => response.json())
                    .then(data => {
                        setPizzas(data.content);
                        setLoading(false);
                    });
            })
    }

    return (
        <>
            <Header/>
            <body className={"body"}>
                <h3>Все пиццы</h3>
                <div className={"body-main-box"}>
                    {pizzaList}
                </div>
            <ReactPaginate
                previousLabel={"Назад"}
                nextLabel={"Вперед"}
                pageCount={numberOfPages}
                onPageChange={changePage}
                containerClassName={"paginationBar"}
                previousLinkClassName={"previousBar"}
                nextLinkClassName={"nextButton"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}
            />
            </body>
        </>
    );
}

export default PizzaList;


