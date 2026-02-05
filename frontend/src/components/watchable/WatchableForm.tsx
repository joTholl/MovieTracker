import {useState} from "react";
import axios from "axios";
import type {WatchableInDto} from "../../types/WatchableInDto.ts";


type WatchableFormProps = {
    onSaved: (id: WatchableInDto) => void;
};

export function WatchableForm({onSaved}: WatchableFormProps) {
    const [watchable, setWatchable] = useState({
        title: "",
        actors: [] as string[],
        duration: "",
        directors: [] as string[],
        releaseDate: "",
        genres: [] as string[],
        episode: 1,
        ageRating: 12,
    });

    const saveWatchable = () => {
        axios.post("http://localhost:8080/api/watchables", watchable).then((res) => {
            onSaved(res.data);
        });
    };

    return (
        <section className="card">
            <h3>Watchable</h3>
            <div className="form-group">
                <input
                    placeholder="Titel"
                    onChange={(e) =>
                        setWatchable({...watchable, title: e.target.value})
                    }
                />
                <input
                    placeholder="Actors (Komma getrennt)"
                    onChange={(e) =>
                        setWatchable({
                            ...watchable,
                            actors: e.target.value.split(",").map((a) => a.trim()),
                        })
                    }
                />
                <input
                    placeholder="Duration (z.B. 00:47)"
                    onChange={(e) =>
                        setWatchable({...watchable, duration: e.target.value})
                    }
                />
                <input
                    placeholder="Directors (Komma)"
                    onChange={(e) =>
                        setWatchable({
                            ...watchable,
                            directors: e.target.value.split(",").map((d) => d.trim()),
                        })
                    }
                />
                <label>Release Date:
                    <input
                        type="date"
                        placeholder="Release Date"
                        onChange={(e) =>
                            setWatchable({...watchable, releaseDate: e.target.value})
                        }
                    />
                </label>
                <input
                    placeholder="Genres (Komma)"
                    onChange={(e) =>
                        setWatchable({
                            ...watchable,
                            genres: e.target.value.split(",").map((g) => g.trim()),
                        })
                    }
                />
                <label>Episode:
                    <input
                        type="number"
                        placeholder="Episode"
                        value={watchable.episode}
                        onChange={(e) =>
                            setWatchable({
                                ...watchable,
                                episode: Number(e.target.value),
                            })
                        }
                    />
                </label>
                <label>Age Rating:
                    <input
                        type="number"
                        placeholder="Age Rating"
                        value={watchable.ageRating}
                        onChange={(e) =>
                            setWatchable({
                                ...watchable,
                                ageRating: Number(e.target.value),
                            })
                        }
                    />
                </label>
                <button className="submitBtn" onClick={saveWatchable}>Watchable speichern</button>
            </div>
        </section>
    );
}
