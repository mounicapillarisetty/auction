import java.util.*;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kolling.
 * @version 2006.03.30
 *
 * @author (of AuctionSkeleton) Lynn Marshall
 * @version 2.0
 * 
 * @author Mounica Pillarisetty
 * @version 10/07/17
 * 
 */
public class Auction
{
    /** The list of Lots in this auction. */
    private ArrayList<Lot> lots;

    /** 
     * The number that will be given to the next lot entered
     * into this auction.  Every lot gets a new number, even if some lots have
     * been removed.  (For example, if lot number 3 has been deleted, we don't
     * reuse it.)
     */
    private int nextLotNumber;

    /** 
     * A boolean variable to state if an auction is open or closed
     */ 
    private boolean isOpen;

    /**
     * Creating a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
        // you need to add something here -- see hint above.
        isOpen = true;
    }
    
    /**
     * 
     * Add a second constructor here.  
     * This constructor takes an Auction as a parameter.  
     * Provided the auction parameter is closed, the constructor creates a new auction containing
     * the unsold lots of the closed auction.  
     * 
     * @param newAuction  If the auction parameter is still open or null, this constructor behaves like the
     *                    default constructor.
     */
    public Auction(Auction newAuction)
    { 
        if(!newAuction.isOpen && newAuction.getNoBids() != null)
        {
            lots = newAuction.getNoBids();
            nextLotNumber = newAuction.nextLotNumber;
            isOpen = true;
        } 
        else 
        {
            lots = new ArrayList<Lot>();
            nextLotNumber = 1;
            isOpen = true;
        }
    }
    
    /**
     * Enter a new lot into the auction.  
     * This function returns false if the auction is not open or if the description is null or otherwise true.
     * 
     * @param description A description of the lot.
     * @return false if auction is closed or if description is null
     */
    public boolean enterLot(String description)
    {
        if (!isOpen || description == null)
        {
            return false;
        } 
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
        return true;
    }

    /**
     * Show the full list of lots in this auction.
     *
     * Print a blank line first, to make our output look nicer. 
     * If there are no lots, print a message indicating this 
     * 
     */
    public void showLots()
    {
        System.out.println();
        if(lots.size() == 0 || lots == null)
        {
            System.out.println("Sorry! There are no lots available.");
        } 
        for(Lot lot : lots) 
        {
            System.out.println(lot.toString());
        }
    }
    
    /**
     * Bid for a lot.
     * Do not assume that the lots are stored in numerical order.
     * Prints a message indicating whether the bid is successful or not.
     *   
     * First print a blank line.  
     * Then print whether or not the bid is successful.
     * If the bid is successful, also print the
     * lot number, high bidder's name, and the bid value.
     * If the bid is not successful, also print the lot number 
     * and high bid (but not the high bidder's name).
     * 
     * Returns false if the auction is closed, the lot doesn't
     * exist, the bidder is null, or the bid was not positive
     * and true otherwise (even if the bid was not high enough).
     *
     * @param number The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     * @return false if the auction is closed, the lot doesn't
     *               exist, the bidder is null, or the bid was not positive
     *       
     */
    
    public boolean bidFor(int lotNumber, Person bidder, long value)
    {
        for (Lot lot : lots)
        {
            if(isOpen && lots != null && bidder != null && value > 0 && lot.getNumber() == lotNumber)
            {
                System.out.println();
                if(lot.bidFor(new Bid(bidder, value))) 
                {
                    System.out.println("The bid was successful!");
                    System.out.println("The lot number is: " + lot.getNumber());
                    System.out.println("The highest bidder's name is: " + bidder.getName());
                    System.out.println("The bid's value is: $" + lot.getHighestBid().getValue());
                }
                else
                { 
                    System.out.println("Sorry the bid was not successful!");
                    System.out.println("The lot number: " + lot.getNumber());
                    System.out.println("There is already a higher bidder. The value is: $" + lot.getHighestBid().getValue());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return the lot with the given number. 
     * Do not assume that the lots are stored in numerical order.   
     *   
     * Returns null if the lot does not exist.
     *
     * @param lotNumber The number of the lot to return
     * @return the Lot with the given number
     */ 
    public Lot getLot(int lotNumber)
    {
        for(Lot lot : lots)
        {
            if(lot.getNumber() == lotNumber)
            {
                return lot; 
            }
        }
        return null;
    }
    
    /**
     * Closes the auction and prints information on the lots.
     * First print a blank line.  Then for each lot,
     * its number and description are printed.
     * If it did sell, the high bidder and bid value are also printed.  
     * If it didn't sell, that information is printed.
     * 
     * @return false if the auction is already closed, true otherwise.
     *
     */
    public boolean close()
    {
        if (isOpen)
        {
            isOpen = false;
            for(Lot lot : lots)
            {
                System.out.println();
                if(lot.getHighestBid() != null)
                { 
                    System.out.println("Lot number " + lot.getNumber() + ": "+ lot.getDescription());
                    System.out.println("The highest bidder for this lot is " + lot.getHighestBid().getBidder().getName()
                                        + " and the value was $" + lot.getHighestBid().getValue());
                } 
                else 
                {
                    System.out.println("Lot number " + lot.getNumber() + ": "+ lot.getDescription() + " was unsold.");
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Returns an ArrayList containing all the items that have no bids so far.
     * (or have not sold if the auction has ended).
     * 
     * @return  ArrayList of the Lots which currently have no bids
     */
    public ArrayList<Lot> getNoBids()
    {
       ArrayList<Lot> noBids = new ArrayList<Lot>();
       for (Lot lot : lots)
       {
           if(lot.getHighestBid() == null)
           {
               noBids.add(lot);
           }
       }
       return noBids; 
    }
    
    /**
     * Remove the lot with the given lot number, as long as the lot has
     * no bids, and the auction is open.  
     * You must use an Iterator object to search the list and,
     * if applicable, remove the item.
     * Do not assume that the lots are stored in numerical order.
     *
     * Returns true if successful, false otherwise (auction closed,
     * lot does not exist, or lot has a bid).
     * 
     * @param number The number of the lot to be removed.
     * @return true if removing an item was successful
     */
    public boolean removeLot(int number)
    {
        if(isOpen && lots != null)
        {
            Iterator<Lot> iter = lots.iterator();
            while(iter.hasNext())
            {
                Lot nextLot = iter.next();
                if (nextLot.getNumber() == number)
                {
                    if(nextLot.getHighestBid() == null) 
                    {
                        iter.remove();
                        return true;
                    } else 
                    {
                        return false;
                    }
                }
            }
        }
        return false;
    }    
}