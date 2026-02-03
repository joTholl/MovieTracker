export default function Filterbar() {
    return(
        <aside className="filter-sidebar">
            <h3>Filter</h3>

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