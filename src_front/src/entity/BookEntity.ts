import {z} from "zod"
import {Moment} from "moment";

const dateSchema = z.preprocess((arg) => {
    if (typeof arg == "string" || arg instanceof Date) return new Date(arg)
}, z.date())

export const BookEntity = z.object({
    id: z.string(),
    title: z.string(),
    author: z.string(),
    releaseDate: dateSchema,
    open: z.boolean(),
    value: z.string(),
})

export type BookForm = {
    title: string, author: string,
    datePicker: Moment, open: boolean | undefined,
    id: string | undefined
}


export type Book = z.infer<typeof BookEntity>
