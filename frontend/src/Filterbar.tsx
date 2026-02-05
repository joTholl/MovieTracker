import {useState} from "react";
import type {FilterDto} from "./types/FilterDto.ts";

type Props = {
    filters: FilterDto[];
    setFilters: (newFilters: FilterDto[]) => void;
};

export default function Filterbar(props: Props) {

    const [titleValue, setTitleValue] = useState<string>("");

    function onTitleChange(input:string) {

        input = input.trim().toLowerCase();
        setTitleValue(input);

        //let hasTitle = false
        //if (props.filters.length === 0) {
        //    hasTitle = props.filters.some((f) => f.searchFilter === "TITLE");
        //}

        const withoutTitle = props.filters.filter((f) => f.searchFilter !== "TITLE");

        if (titleValue.length >= 1) {
            const newFilter: FilterDto = {
                searchFilter: "TITLE",
                input: titleValue
            };

            props.setFilters([...withoutTitle, newFilter]);
        }
        else {
            props.setFilters(withoutTitle);
        }
    }

    return(
        <aside className="filter-sidebar">
            <h3>Filter</h3>

            <div className="filter-group">
                <h4>Titel</h4>
                <label>
                    <input
                    type="text"
                    placeholder="enter titel"
                    value={titleValue}
                    onChange={(e) => onTitleChange(e.target.value)}
                    />
                </label>
            </div>

            <div className="filter-group">
                <h4>Genre</h4>
                <label><input type="checkbox"/> Drama</label>
                <label><input type="checkbox"/> Horror</label>
                <label><input type="checkbox"/> Comedy</label>
                <label><input type="checkbox"/> Romance</label>
                <label><input type="checkbox"/> Anime</label>
            </div>
            <div className="filter-group">
                <h4>Streaming Platforms</h4>
                <label><input type="checkbox"/> Netflix</label>
                <label><input type="checkbox"/> Prime Video</label>
                <label><input type="checkbox"/> Disey+</label>
                <label><input type="checkbox"/> WoW</label>
                <label><input type="checkbox"/> AppleTV</label>
            </div>

            <div className="filter-group">
                <h4>Contributors</h4>
                <label><input type="text" placeholder="Enter Actor"/></label>
                <label><input type="text" placeholder="Enter Regisseur"/></label>
            </div>

            <div className="filter-group">
                <h4>Age Rating</h4>
                <label><input type="checkbox"/> 0</label>
                <label><input type="checkbox"/> 6</label>
                <label><input type="checkbox"/> 12</label>
                <label><input type="checkbox"/> 16</label>
                <label><input type="checkbox"/> 18</label>
            </div>

            <div className="filter-group">
                <h4>Release</h4>
                <label><input type="text" placeholder="Start"/></label>
                <label><input type="text" placeholder="End"/></label>
            </div>
            <div className="filter-group">
                <h4>Duration</h4>
                <label><input type="checkbox"/> &lt;60 min</label>
                <label><input type="checkbox"/> 60-90 min</label>
                <label><input type="checkbox"/> 90-120 min</label>
                <label><input type="checkbox"/> 120+ min</label>
            </div>

        </aside>
    )
}