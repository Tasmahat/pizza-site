import React from "react";
import AddPizzaButton from "./AddPizzaButton";


    function Header() {
        return (
            <header className="App-header">
                <Menu/>
            </header>
        );
    }

    function Menu() {
        return (
            <div className={"main-menu"}>
                <form action="/search" method="GET">
                    <input type="text" name="query" placeholder="Найди свою пиццу!" className={"main-menu-search-bar"}/>
                    <button type="submit" className={"main-menu-search-button"}>Поиск</button>
                </form>
                <AddPizzaButton></AddPizzaButton>
                <p className={"greeting"}>Приветствуем, {localStorage.getItem('username')}!</p>
            </div>
        )
    }
export default Header;